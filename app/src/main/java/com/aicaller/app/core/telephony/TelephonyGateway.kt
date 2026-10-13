package com.aicaller.app.core.telephony

import com.aicaller.app.core.model.CallDirection
import com.aicaller.app.core.model.CallRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Platform boundary for later Android Telecom/default-dialer integration.
 * This Phase 01 implementation intentionally performs no carrier calls.
 */
interface TelephonyGateway {
    val callEvents: Flow<CallRecord>
    suspend fun canPlaceCalls(): Boolean
    suspend fun requestOutgoingCallConfirmation(displayName: String, phoneNumber: String): CallRequestResult
}

sealed interface CallRequestResult {
    data object NotImplemented : CallRequestResult
    data object PermissionRequired : CallRequestResult
    data object ConfirmationRequired : CallRequestResult
    data class Rejected(val reason: String) : CallRequestResult
}

class DemoTelephonyGateway : TelephonyGateway {
    override val callEvents: Flow<CallRecord> = emptyFlow()
    override suspend fun canPlaceCalls(): Boolean = false
    override suspend fun requestOutgoingCallConfirmation(
        displayName: String,
        phoneNumber: String
    ): CallRequestResult = CallRequestResult.NotImplemented
}
