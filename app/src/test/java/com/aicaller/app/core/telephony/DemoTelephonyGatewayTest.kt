package com.aicaller.app.core.telephony

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DemoTelephonyGatewayTest {
    @Test
    fun demoGatewayCannotPlaceRealCalls() = runBlocking {
        val gateway = DemoTelephonyGateway()
        assertFalse(gateway.canPlaceCalls())
        assertTrue(gateway.requestOutgoingCallConfirmation("Sample", "+910000000000") is CallRequestResult.NotImplemented)
    }
}
