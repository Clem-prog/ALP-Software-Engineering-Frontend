package com.example.alp_software_engineering_frontend.services

import com.example.alp_software_engineering_frontend.models.GetAllRoomsResponse
import com.example.alp_software_engineering_frontend.models.GetRoomResponse
import com.example.alp_software_engineering_frontend.models.RoomUpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoomAPIService {
    @GET("api/rooms")
    fun getAllRooms (@Header("X-API-TOKEN") token: String): Call<GetAllRoomsResponse>

    @GET("api/rooms/{id}")
    fun getRoomById (@Header("X-API-TOKEN") token: String, @Path("id") roomId: Int): Call<GetRoomResponse>

    @GET("/api/rooms/occupant")
    fun getRoomByOccupant (@Header("X-API-TOKEN") token: String): Call<GetRoomResponse>

    @PUT("api/rooms/{id}")
    fun updateRoomStatus(@Header("X-API-TOKEN") token: String, @Path("id") roomId: Int, @Body roomModel: RoomUpdateRequest): Call<GetRoomResponse>
}

//if none of the things with {id} doesnt work, it may be because it's not {roomid}
//yes it includes changing the @Path into @Path("roomid")
//if it doesnt then let it be yknow like the hit song made by the beatles ahah im stressing