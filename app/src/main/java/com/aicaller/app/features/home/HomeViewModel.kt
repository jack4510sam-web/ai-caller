package com.aicaller.app.features.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val assistantReady: Boolean = false,
    val statusMessage: String = "Voice service is not connected yet.",
    val demoMode: Boolean = true
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onTryAssistant() {
        _uiState.value = _uiState.value.copy(
            statusMessage = "Demo mode: connect the voice service in a future phase.",
            assistantReady = false
        )
    }
}
