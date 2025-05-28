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
import retrofit2.http.Path

interface PaymentAPIService {
    @GET("api/payments/{roomid}")
    fun getAllPayments (@Header("X-API-TOKEN") token: String, @Path("roomid") roomId: Int): Call<GetAllPaymentsResponse>

    @GET("api/payments/latest/{roomid}")
    fun getLatestPayment (@Header("X-API-TOKEN") token: String, @Path("roomid") roomId: Int): Call<GetPaymentResponse>

    @POST("api/payments")
    fun createPayment(@Header("X-API-TOKEN") token: String, @Body paymentModel: PaymentRequest): Call<GetPaymentResponse>

    @DELETE("api/payments/{id}")
    fun deletePayment(@Header("X-API-TOKEN") token: String, @Path("id") paymentId: Int): Call<GeneralResponseModel>
}