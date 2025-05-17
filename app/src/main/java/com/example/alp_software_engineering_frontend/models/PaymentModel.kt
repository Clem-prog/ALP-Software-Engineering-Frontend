package com.example.alp_software_engineering_frontend.models
import java.util.Date

data class GetAllPaymentsResponse (
    val data : List<PaymentModel>
)

data class GetPaymentResponse (
    val data : PaymentModel
)

data class PaymentModel (
    val id: Int,
    val transfer_receipt: String,
    val date: Date,
    val roomId: Int,
    val userId: Int
)

data class PaymentRequest (
    val transfer_receipt: String,
    val date: Date,
    val roomId: Int,
    val userId: Int
)


