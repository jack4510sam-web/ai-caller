package com.aicaller.app.core.privacy

data class ConsentState(
    val userAcknowledgedDemo: Boolean = false,
    val recordingConsentObtained: Boolean = false,
    val remoteProcessingConsentObtained: Boolean = false
)

object ConsentPolicy {
    /** Recording/transcription must not start until explicit consent is recorded. */
    fun mayRecord(state: ConsentState): Boolean =
        state.recordingConsentObtained && state.remoteProcessingConsentObtained

    /** A UI preview is safe to show without suggesting a real call is active. */
    fun previewLabel(isDemo: Boolean): String =
        if (isDemo) "DEMO PREVIEW · NO REAL CALL" else "CALL STATUS"
}
