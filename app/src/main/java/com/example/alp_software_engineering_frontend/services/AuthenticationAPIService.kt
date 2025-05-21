package com.example.alp_software_engineering_frontend.services

import com.example.alp_software_engineering_frontend.models.GetUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationAPIService {
    @POST("api/login")
    fun login(@Body loginMap: HashMap<String, String>): Call<GetUserResponse>
}