package com.example.alp_software_engineering_frontend.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllUsersResponse
import com.example.alp_software_engineering_frontend.models.GetUserResponse
import com.example.alp_software_engineering_frontend.services.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call

interface UserRepository {
    val currentUserToken: Flow<String>
    val currentIsAdmin: Flow<Boolean> // debatable if its needed or not
    val currentUserId: Flow<Int>
    fun logout(token: String): Call<GeneralResponseModel>
    fun getUserById(token: String, userId: Int): Call<GetUserResponse>
    fun getAllUser(token: String): Call<GetAllUsersResponse>
    suspend fun saveUserToken(token: String)
    suspend fun saveIsAdmin(isAdmin: Boolean)
    suspend fun saveUserId(userId: Int)
}

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userAPIService: UserAPIService
): UserRepository {
    private companion object {
        val USER_TOKEN = stringPreferencesKey("token")
        val IS_ADMIN = booleanPreferencesKey("isAdmin") // debatable might not need it
        val USER_ID = intPreferencesKey("userId")
    }

    override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USER_TOKEN] ?: "Unknown"
    }

    override val currentIsAdmin: Flow<Boolean> = userDataStore.data.map { preferences ->
        preferences[IS_ADMIN] ?: false  // debatable might not need it
    }

    override val currentUserId: Flow<Int> = userDataStore.data.map { preferences ->
        preferences[USER_ID] ?: 0
    }

    override suspend fun saveUserToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    override suspend fun saveIsAdmin(isAdmin: Boolean) {
        userDataStore.edit { preferences ->
            preferences[IS_ADMIN] = isAdmin
        }
    }

    override suspend fun saveUserId(userId: Int) {
        userDataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    override fun logout(token: String): Call<GeneralResponseModel> {
        return userAPIService.logout(token)
    }


    override fun getUserById(token: String, userId: Int): Call<GetUserResponse> {
        return userAPIService.getUserById(token, userId)
    }


    override fun getAllUser(token: String): Call<GetAllUsersResponse> {
        return userAPIService.getAllUsers(token)
    }
}