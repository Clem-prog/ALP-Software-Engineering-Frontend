package com.example.alp_software_engineering_frontend.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alp_software_engineering_frontend.D4C
import com.example.alp_software_engineering_frontend.models.ErrorModel
import com.example.alp_software_engineering_frontend.models.GetAllUsersResponse
import com.example.alp_software_engineering_frontend.models.GetUserResponse
import com.example.alp_software_engineering_frontend.repositories.UserRepository
import com.example.alp_software_engineering_frontend.uiStates.UserDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var dataStatus: UserDataStatusUIState by mutableStateOf(UserDataStatusUIState.Start)
        private set

    val isAdmin: StateFlow<Boolean> = userRepository.currentIsAdmin.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val id: StateFlow<Int> = userRepository.currentUserId.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    fun getAllUser(
        token: String
    ) {
        viewModelScope.launch {
            dataStatus = UserDataStatusUIState.Loading

            try {
                val call = userRepository.getAllUser(token)

                call.enqueue(object : Callback<GetAllUsersResponse> {
                    override fun onResponse(
                        call: Call<GetAllUsersResponse>,
                        res: Response<GetAllUsersResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = UserDataStatusUIState.GetAllSuccess(res.body()!!.data)
                        } else {
                            val errorBody = res.errorBody()?.charStream()
                            val errorMessage = if (errorBody != null) {
                                Gson().fromJson(errorBody, ErrorModel::class.java)
                            } else {
                                null
                            }

                            val errorText = errorMessage?.errors ?: "Unknown error occurred"
                            dataStatus = UserDataStatusUIState.Failed(errorText)
                        }
                    }

                    override fun onFailure(call: Call<GetAllUsersResponse>, t: Throwable) {
                        dataStatus = UserDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = UserDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun getUserById(token: String, userId: Int) {
        viewModelScope.launch {
            dataStatus = UserDataStatusUIState.Loading

            try {
                val call = userRepository.getUserById(token, userId)

                call.enqueue(object : Callback<GetUserResponse> {
                    override fun onResponse(call: Call<GetUserResponse>, res: Response<GetUserResponse>) {
                        if (res.isSuccessful) {
                            dataStatus = UserDataStatusUIState.Success(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = UserDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                        dataStatus = UserDataStatusUIState.Failed(t.localizedMessage)
                        Log.d("get-user-error", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (error: IOException) {
                dataStatus = UserDataStatusUIState.Failed(error.localizedMessage)
                Log.d("get-user-error", "GET USER ERROR: ${error.localizedMessage}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as D4C)
                val userRepository = application.container.userRepository
                UserViewModel(userRepository)
            }
        }
    }
}