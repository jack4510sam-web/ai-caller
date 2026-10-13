package com.aicaller.app.core.voice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Small Android client for the deployed AI Caller backend.
 *
 * Pass a Firebase ID token obtained from the signed-in Firebase user. The token
 * and returned short-lived provider secret are never written to logs or disk.
 * This only creates a session; WebRTC audio transport is a separate integration.
 */
class VoiceSessionApi(private val baseUrl: String) {
    data class SessionCredentials(
        val clientSecret: String,
        val expiresAt: String,
        val sessionId: String
    )

    suspend fun createSession(firebaseIdToken: String): Result<SessionCredentials> =
        withContext(Dispatchers.IO) {
            runCatching {
                require(firebaseIdToken.isNotBlank()) { "Sign in before starting an AI voice session." }
                val normalizedBase = baseUrl.trim().trimEnd('/')
                require(normalizedBase.startsWith("https://")) {
                    "Configure the HTTPS URL of your deployed AI Caller backend."
                }

                val connection = (URL("$normalizedBase/v1/voice/session").openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    connectTimeout = 10_000
                    readTimeout = 15_000
                    doOutput = true
                    setRequestProperty("Authorization", "Bearer $firebaseIdToken")
                    setRequestProperty("Accept", "application/json")
                    setRequestProperty("Content-Type", "application/json")
                }

                try {
                    connection.outputStream.use { it.write("{}".toByteArray(Charsets.UTF_8)) }
                    val status = connection.responseCode
                    val stream = if (status in 200..299) connection.inputStream else connection.errorStream
                    val body = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }.orEmpty()
                    if (status !in 200..299) {
                        val safeMessage = when (status) {
                            401, 403 -> "Sign-in was rejected. Please sign in again."
                            429 -> "Too many voice-session requests. Please wait and retry."
                            else -> "Voice service unavailable (HTTP $status)."
                        }
                        throw IllegalStateException(safeMessage)
                    }

                    val json = JSONObject(body)
                    val secret = json.optString("clientSecret")
                    val expires = json.optString("expiresAt")
                    val sessionId = json.optString("sessionId")
                    check(secret.isNotBlank() && expires.isNotBlank() && sessionId.isNotBlank()) {
                        "Voice service returned an incomplete session."
                    }
                    SessionCredentials(secret, expires, sessionId)
                } finally {
                    connection.disconnect()
                }
            }
        }
}
