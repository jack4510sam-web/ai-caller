package com.aicaller.app.core.model

import java.time.Instant

enum class CallDirection { INCOMING, OUTGOING }
enum class CallStatus { PLANNED, RINGING, ACTIVE, COMPLETED, MISSED, DECLINED, FAILED }

data class CallRecord(
    val id: String,
    val displayName: String,
    val direction: CallDirection,
    val status: CallStatus,
    val startedAt: Instant? = null,
    val durationSeconds: Long = 0,
    val summary: String? = null,
    val transcriptAvailable: Boolean = false
)

data class AssistantProfile(
    val id: String,
    val name: String,
    val purpose: String,
    val languageTag: String = "en-IN",
    val voiceId: String? = null,
    val enabled: Boolean = false
)
