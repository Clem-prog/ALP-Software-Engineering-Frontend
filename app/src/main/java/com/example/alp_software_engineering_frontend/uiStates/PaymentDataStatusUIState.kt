package com.example.alp_software_engineering_frontend.uiStates

interface PaymentDataStatusUIState {
    object Start: PaymentDataStatusUIState
    object Loading: PaymentDataStatusUIState
    data class Failed(val errorMessage: String): PaymentDataStatusUIState
}