package com.example.alp_software_engineering_frontend

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.alp_software_engineering_frontend.repositories.AuthenticationRepository
import com.example.alp_software_engineering_frontend.repositories.NetworkAuthenticationRepository
import com.example.alp_software_engineering_frontend.repositories.NetworkPaymentRepository
import com.example.alp_software_engineering_frontend.repositories.NetworkRoomRepository
import com.example.alp_software_engineering_frontend.repositories.NetworkUserRepository
import com.example.alp_software_engineering_frontend.repositories.PaymentRepository
import com.example.alp_software_engineering_frontend.repositories.RoomRepository
import com.example.alp_software_engineering_frontend.repositories.UserRepository
import com.example.alp_software_engineering_frontend.services.AuthenticationAPIService
import com.example.alp_software_engineering_frontend.services.PaymentAPIService
import com.example.alp_software_engineering_frontend.services.RoomAPIService
import com.example.alp_software_engineering_frontend.services.UserAPIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val paymentRepository: PaymentRepository
    val roomRepository: RoomRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
): AppContainer {
    // change it to your own local ip please
    private val baseUrl = "http://10.0.2.2:3000/"

    // RETROFIT SERVICE
    // delay object creation until needed using lazy
    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userRetrofitService: UserAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserAPIService::class.java)
    }

    private val paymentRetrofitService: PaymentAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(PaymentAPIService::class.java)
    }

    private val roomRetrofitService: RoomAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(RoomAPIService::class.java)
    }

    // REPOSITORY INIT
    // Passing in the required objects is called dependency injection (DI). It is also known as inversion of control.
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val paymentRepository: PaymentRepository by lazy {
        NetworkPaymentRepository(paymentRetrofitService)
    }

    override val roomRepository: RoomRepository by lazy {
        NetworkRoomRepository(roomRetrofitService)
    }

    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}