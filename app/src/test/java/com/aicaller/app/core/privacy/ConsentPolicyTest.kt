package com.aicaller.app.core.privacy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ConsentPolicyTest {
    @Test
    fun recordingRequiresBothExplicitConsents() {
        assertFalse(ConsentPolicy.mayRecord(ConsentState()))
        assertFalse(ConsentPolicy.mayRecord(ConsentState(recordingConsentObtained = true)))
        assertFalse(ConsentPolicy.mayRecord(ConsentState(remoteProcessingConsentObtained = true)))
        assertTrue(ConsentPolicy.mayRecord(ConsentState(recordingConsentObtained = true, remoteProcessingConsentObtained = true)))
    }

    @Test
    fun demoPreviewIsClearlyLabelled() {
        assertEquals("DEMO PREVIEW · NO REAL CALL", ConsentPolicy.previewLabel(true))
    }
}
