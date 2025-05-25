package com.example.alp_software_engineering_frontend.repositories

import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllPaymentsResponse
import com.example.alp_software_engineering_frontend.models.GetPaymentResponse
import com.example.alp_software_engineering_frontend.models.PaymentRequest
import com.example.alp_software_engineering_frontend.services.PaymentAPIService
import retrofit2.Call
import java.util.Date

interface PaymentRepository {
    fun getAllPayments(token: String, roomId: Int): Call<GetAllPaymentsResponse>
    fun getLatestPayment(token: String, roomId: Int): Call<GetPaymentResponse>
    fun createPayment(token: String, transfer_receipt: String, date: Date, roomId: Int): Call<GetPaymentResponse>
    fun deletePayment(token: String, paymentId: Int): Call<GeneralResponseModel>
}

class NetworkPaymentRepository(
    private val paymentAPIService: PaymentAPIService
): PaymentRepository {

    override fun getAllPayments(
        token: String,
        roomId: Int
    ): Call<GetAllPaymentsResponse> {
        return paymentAPIService.getAllPayments(token, roomId)
    }

    override fun getLatestPayment(
        token: String,
        roomId: Int
    ): Call<GetPaymentResponse> {
        return paymentAPIService.getLatestPayment(token, roomId)
    }

    override fun createPayment(
        token: String,
        transfer_receipt: String,
        date: Date,
        roomId: Int
    ): Call<GetPaymentResponse> {
        return paymentAPIService.createPayment(token, PaymentRequest(transfer_receipt, date, roomId))
    }

    override fun deletePayment(
        token: String,
        paymentId: Int
    ): Call<GeneralResponseModel> {
        return paymentAPIService.deletePayment(token, paymentId)
    }
}