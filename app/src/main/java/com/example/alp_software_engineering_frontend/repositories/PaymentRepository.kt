package com.example.alp_software_engineering_frontend.repositories

import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllPaymentsResponse
import com.example.alp_software_engineering_frontend.models.GetPaymentResponse
import com.example.alp_software_engineering_frontend.models.PaymentRequest
import com.example.alp_software_engineering_frontend.services.PaymentAPIService
import retrofit2.Call

interface PaymentRepository {
    fun getAllPayments(token: String): Call<GetAllPaymentsResponse>
    fun getLatestPayment(token: String, roomId: Int): Call<GetPaymentResponse>
    fun createPayment(token: String, request: PaymentRequest): Call<GetPaymentResponse>
    fun deletePayment(token: String, paymentId: Int): Call<GeneralResponseModel>
}

class NetworkPaymentRepository(
    private val paymentAPIService: PaymentAPIService
): PaymentRepository {

    override fun getAllPayments(token: String): Call<GetAllPaymentsResponse> {
        return paymentAPIService.getAllPayments(token)
    }

    override fun getLatestPayment(token: String, roomId: Int): Call<GetPaymentResponse> {
        return paymentAPIService.getLatestPayment(token, roomId)
    }

    override fun createPayment(token: String, request: PaymentRequest): Call<GetPaymentResponse> {
        return paymentAPIService.createPayment(token, request)
    }

    override fun deletePayment(token: String, paymentId: Int): Call<GeneralResponseModel> {
        return paymentAPIService.deletePayment(token, paymentId)
    }
}