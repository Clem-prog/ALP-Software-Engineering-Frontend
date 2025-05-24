package com.example.alp_software_engineering_frontend.repositories
import com.example.alp_software_engineering_frontend.models.GetAllRoomsResponse
import com.example.alp_software_engineering_frontend.models.GetRoomResponse
import com.example.alp_software_engineering_frontend.models.RoomUpdateRequest
import com.example.alp_software_engineering_frontend.services.RoomAPIService
import retrofit2.Call

interface RoomRepository {
    fun getRoomById(token: String, roomId: Int): Call<GetRoomResponse>
    fun getAllRooms(token: String): Call<GetAllRoomsResponse>
    fun updateRoomStatus(token: String, roomId: Int, paymentStatus: String): Call<GetRoomResponse>
}

class NetworkRoomRepository(
    private val roomAPIService: RoomAPIService
): RoomRepository {

    override fun getRoomById(token: String, roomId: Int): Call<GetRoomResponse> {
        return roomAPIService.getRoomById(token, roomId)
    }

    override fun getAllRooms(token: String): Call<GetAllRoomsResponse> {
        return roomAPIService.getAllRooms(token)
    }

    override fun updateRoomStatus(token: String, roomId: Int, paymentStatus: String): Call<GetRoomResponse> {
        return roomAPIService.updateRoomStatus(token, roomId, RoomUpdateRequest(paymentStatus))
    }
}

