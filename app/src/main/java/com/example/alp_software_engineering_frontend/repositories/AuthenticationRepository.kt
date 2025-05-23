package com.example.alp_software_engineering_frontend.repositories
import com.example.alp_software_engineering_frontend.models.GetUserResponse
import com.example.alp_software_engineering_frontend.services.AuthenticationAPIService
import retrofit2.Call

interface AuthenticationRepository {
    fun login(email: String, password: String): Call<GetUserResponse>
}

class NetworkAuthenticationRepository(
    private val authenticationAPIService: AuthenticationAPIService
): AuthenticationRepository {

    override fun login(email: String, password: String): Call<GetUserResponse> {
        var loginMap = HashMap<String, String>()

        loginMap["email"] = email
        loginMap["password"] = password

        return authenticationAPIService.login(loginMap)
    }
}