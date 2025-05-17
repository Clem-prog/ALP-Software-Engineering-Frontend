package com.example.alp_software_engineering_frontend.services

import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllUsersResponse
import com.example.alp_software_engineering_frontend.models.GetUserResponse
import com.example.alp_software_engineering_frontend.models.UserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPIService {
    @GET("api/users")
    fun getAllUsers (@Header("X-API-TOKEN") token: String): Call<GetAllUsersResponse>

    @GET("api/users/{id}")
    fun getUserById (@Header("X-API-TOKEN") token: String, @Path("id") userId: Int): Call<GetUserResponse>

    @POST("api/users")
    fun createUser(@Header("X-API-TOKEN") token: String, @Body userModel: UserRequest): Call<GetUserResponse>

    @PUT("api/users/{id}")
    fun updateUser(@Header("X-API-TOKEN") token: String, @Path("id") userId: Int, @Body userModel: UserRequest): Call<GetUserResponse>

    @DELETE("api/users/{id}")
    fun deleteUser(@Header("X-API-TOKEN") token: String, @Path("id") userId: Int): Call<GeneralResponseModel>
}

// User service still needs correction i think since when I saw the service of our
// old code it utilizes login response and update response for a few of the functions