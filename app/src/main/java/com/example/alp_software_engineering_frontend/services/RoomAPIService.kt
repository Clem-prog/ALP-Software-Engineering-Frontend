package com.example.alp_software_engineering_frontend.services

import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllRoomsResponse
import com.example.alp_software_engineering_frontend.models.GetRoomResponse
import com.example.alp_software_engineering_frontend.models.RoomRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface RoomAPIService {
    @GET("api/rooms")
    fun getAllRooms (@Header("X-API-TOKEN") token: String): Call<GetAllRoomsResponse>

    @GET("api/rooms/{id}")
    fun getRoomById (@Header("X-API-TOKEN") token: String, @Path("id") roomId: Int): Call<GetRoomResponse>

    @POST("api/rooms")
    fun createRoom(@Header("X-API-TOKEN") token: String, @Body roomModel: RoomRequest): Call<GetRoomResponse>

    @PUT("api/rooms/{id}")
    fun updateRoom(@Header("X-API-TOKEN") token: String, @Path("id") roomId: Int, @Body roomModel: RoomRequest): Call<GetRoomResponse>

    @DELETE("api/rooms/{id}")
    fun deleteRoom(@Header("X-API-TOKEN") token: String, @Path("id") roomId: Int): Call<GeneralResponseModel>
}