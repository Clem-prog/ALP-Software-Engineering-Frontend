package com.example.alp_software_engineering_frontend.services

import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllPaymentsResponse
import com.example.alp_software_engineering_frontend.models.GetPaymentResponse
import com.example.alp_software_engineering_frontend.models.PaymentRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PaymentAPIService {
    @GET("api/payments")
    fun getAllPayments (@Header("X-API-TOKEN") token: String): Call<GetAllPaymentsResponse>

    @GET("api/payments/{id}")
    fun getPaymentById (@Header("X-API-TOKEN") token: String, @Path("id") paymentId: Int): Call<GetPaymentResponse>

    @POST("api/payments")
    fun createPayment(@Header("X-API-TOKEN") token: String, @Body paymentModel: PaymentRequest): Call<GetPaymentResponse>

    @PUT("api/payments/{id}")
    fun updatePayment(@Header("X-API-TOKEN") token: String, @Path("id") paymentId: Int, @Body paymentModel: PaymentRequest): Call<GetPaymentResponse>

    @DELETE("api/payments/{id}")
    fun deletePayment(@Header("X-API-TOKEN") token: String, @Path("id") paymentId: Int): Call<GeneralResponseModel>
}