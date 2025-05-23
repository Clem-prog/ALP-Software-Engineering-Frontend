package com.example.alp_software_engineering_frontend.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.alp_software_engineering_frontend.enums.PagesEnum
import com.example.alp_software_engineering_frontend.models.ErrorModel
import com.example.alp_software_engineering_frontend.models.GetUserResponse
import com.example.alp_software_engineering_frontend.repositories.AuthenticationRepository
import com.example.alp_software_engineering_frontend.repositories.UserRepository
import com.example.alp_software_engineering_frontend.uiStates.AuthenticationStatusUIState
import com.example.alp_software_engineering_frontend.uiStates.AuthenticationUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _authenticationUIState = MutableStateFlow(AuthenticationUIState())

    val authenticationUIState: StateFlow<AuthenticationUIState>
        get() {
            return _authenticationUIState.asStateFlow()
        }

    var dataStatus: AuthenticationStatusUIState by mutableStateOf(AuthenticationStatusUIState.Start)
        private set

    var usernameInput by mutableStateOf("")
        private set

    var passwordInput by mutableStateOf("")
        private set

    var emailInput by mutableStateOf("")
        private set

    var isAdminInput by mutableStateOf(false)
        private set

    fun changeEmailInput(emailInput: String) {
        this.emailInput = emailInput
    }

    fun changeUsernameInput(usernameInput: String) {
        this.usernameInput = usernameInput
    }

    fun changePasswordInput(passwordInput: String) {
        this.passwordInput = passwordInput
    }

    fun changeIsAdminInput(isAdminInput: Boolean) {
        this.isAdminInput = isAdminInput
    }

    fun loginUser(
        navController: NavHostController
    ) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading
            try {
                val call = authenticationRepository.login(emailInput, passwordInput)
                call.enqueue(object: Callback<GetUserResponse> {
                    override fun onResponse(call: Call<GetUserResponse>, res: Response<GetUserResponse>) {
                        if (res.isSuccessful) {
                            saveUserInfo(res.body()!!.data.token!!, res.body()!!.data.isAdmin, res.body()!!.data.id)

                            dataStatus = AuthenticationStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            if (!res.body()!!.data.isAdmin) {
                                navController.navigate(PagesEnum.HomeTenant.name) {
                                    popUpTo(PagesEnum.Login.name) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                navController.navigate(PagesEnum.HomeAdmin.name) {
                                    popUpTo(PagesEnum.Login.name) {
                                        inclusive = true
                                    }
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "LOGIN ERROR: $error")
            }
        }
    }

    fun saveUserInfo(token: String, isAdmin: Boolean, userId: Int) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveIsAdmin(isAdmin)
            userRepository.saveUserId(userId)
        }
    }

    fun resetViewModel() {
        changeEmailInput("")
        changePasswordInput("")
        _authenticationUIState.update { currentState ->
            currentState.copy(
                buttonEnabled = false
            )
        }

        dataStatus = AuthenticationStatusUIState.Start
    }
}