package com.aicaller.app.features.home

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeViewModelTest {
    @Test
    fun initialStateIsHonestDemoMode() {
        val viewModel = HomeViewModel()
        val state = viewModel.uiState.value
        assertFalse(state.assistantReady)
        assertTrue(state.demoMode)
        assertTrue(state.statusMessage.contains("not connected", ignoreCase = true))
    }

    @Test
    fun tryAssistantExplainsServiceIsNotConnected() {
        val viewModel = HomeViewModel()
        viewModel.onTryAssistant()
        val state = viewModel.uiState.value
        assertFalse(state.assistantReady)
        assertEquals(true, state.demoMode)
        assertTrue(state.statusMessage.contains("Demo mode"))
    }
}
