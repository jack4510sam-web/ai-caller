package com.aicaller.app.core.voice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class VoiceAgentConfig(
    val agentId: String,
    val languageTag: String,
    val purpose: String
)

sealed interface VoiceEvent {
    data class Status(val message: String) : VoiceEvent
    data class Transcript(val speaker: Speaker, val text: String) : VoiceEvent
    data class Error(val safeMessage: String) : VoiceEvent
    data object Ended : VoiceEvent
}

enum class Speaker { USER, ASSISTANT }

/**
 * Provider-neutral voice boundary. Implementations should obtain short-lived
 * session credentials from a trusted backend; never embed provider secrets here.
 */
interface VoiceAgentGateway {
    val events: Flow<VoiceEvent>
    suspend fun isConfigured(): Boolean
    suspend fun startPreview(config: VoiceAgentConfig): Result<Unit>
    suspend fun stop()
}

class DemoVoiceAgentGateway : VoiceAgentGateway {
    override val events: Flow<VoiceEvent> = emptyFlow()
    override suspend fun isConfigured(): Boolean = false
    override suspend fun startPreview(config: VoiceAgentConfig): Result<Unit> =
        Result.failure(IllegalStateException("Voice service is not connected."))
    override suspend fun stop() = Unit
}
