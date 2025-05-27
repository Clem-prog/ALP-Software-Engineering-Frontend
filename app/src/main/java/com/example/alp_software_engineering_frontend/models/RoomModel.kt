package com.example.alp_software_engineering_frontend.models

import com.example.alp_software_engineering_frontend.enums.PaymentEnum
import java.util.Date

data class GetAllRoomsResponse (
    val data : List<RoomModel>
)

data class GetRoomResponse (
    val data : RoomModel
)

data class RoomModel (
    val id: Int,
    val room_number: String,
    val room_type: String,
    val pricePerMonth: Double,
    val dueDate: Date,
    val paymentStatus: String,
    val occupantId: Int?,
    val occupant: UserModel
)

data class RoomUpdateRequest (
    val paymentStatus: String
)