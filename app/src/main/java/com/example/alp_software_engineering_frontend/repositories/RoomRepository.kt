package com.example.alp_software_engineering_frontend.repositories
import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllRoomsResponse
import com.example.alp_software_engineering_frontend.models.GetRoomResponse
import com.example.alp_software_engineering_frontend.models.RoomUpdateRequest
import com.example.alp_software_engineering_frontend.services.RoomAPIService
import retrofit2.Call

interface RoomRepository {
    fun getRoomById(token: String, roomId: Int): Call<GetRoomResponse>
    fun getAllRooms(token: String): Call<GetAllRoomsResponse>
    fun getRoomByOccupant(token: String): Call<GetRoomResponse>
    fun updateRoomStatus(token: String, roomId: Int, paymentStatus: String): Call<GeneralResponseModel>
}

class NetworkRoomRepository(
    private val roomAPIService: RoomAPIService
): RoomRepository {

    override fun getRoomById(
        token: String,
        roomId: Int
    ): Call<GetRoomResponse> {
        return roomAPIService.getRoomById(token, roomId)
    }

    override fun getAllRooms(token: String): Call<GetAllRoomsResponse> {
        return roomAPIService.getAllRooms(token)
    }

    override fun getRoomByOccupant(token: String): Call<GetRoomResponse> {
        return roomAPIService.getRoomByOccupant(token)
    }

    override fun updateRoomStatus(
        token: String,
        roomId: Int,
        paymentStatus: String
    ): Call<GeneralResponseModel> {
        return roomAPIService.updateRoomStatus(token, roomId, RoomUpdateRequest(paymentStatus))
    }
}

