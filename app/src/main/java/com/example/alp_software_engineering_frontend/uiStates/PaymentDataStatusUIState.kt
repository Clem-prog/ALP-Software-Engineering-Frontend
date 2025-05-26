package com.example.alp_software_engineering_frontend.uiStates

import com.example.alp_software_engineering_frontend.models.PaymentModel

interface PaymentDataStatusUIState {
    data class GetAllSuccess(val paymentModelData: List<PaymentModel>): PaymentDataStatusUIState
    data class Success(val paymentModelData: PaymentModel): PaymentDataStatusUIState
    data class Deleted(val data: String): PaymentDataStatusUIState
    object Start: PaymentDataStatusUIState
    object Loading: PaymentDataStatusUIState
    data class Failed(val errorMessage: String): PaymentDataStatusUIState
}