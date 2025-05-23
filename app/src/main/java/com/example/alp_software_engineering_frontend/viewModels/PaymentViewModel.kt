package com.example.alp_software_engineering_frontend.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.alp_software_engineering_frontend.repositories.PaymentRepository
import com.example.alp_software_engineering_frontend.uiStates.PaymentDataStatusUIState

class PaymentViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    var dataStatus: PaymentDataStatusUIState by mutableStateOf(PaymentDataStatusUIState.Start)
        private set
}