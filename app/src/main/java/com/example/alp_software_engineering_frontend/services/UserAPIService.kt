package com.example.alp_software_engineering_frontend.services

import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllUsersResponse
import com.example.alp_software_engineering_frontend.models.GetUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPIService {
    @POST("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

    @GET("api/users")
    fun getAllUsers (@Header("X-API-TOKEN") token: String): Call<GetAllUsersResponse>

    @GET("api/users/{id}")
    fun getUserById (@Header("X-API-TOKEN") token: String, @Path("id") userId: Int): Call<GetUserResponse>
}

// User service still needs correction i think since when I saw the service of our
// old code it utilizes login response and update response for a few of the functions