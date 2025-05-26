package com.example.alp_software_engineering_frontend.uiStates

import com.example.alp_software_engineering_frontend.models.UserModel

interface AuthenticationStatusUIState {
    data class Success(val userModelData: UserModel): AuthenticationStatusUIState
    object Start: AuthenticationStatusUIState
    object Loading: AuthenticationStatusUIState
    data class Failed(val errorMessage: String): AuthenticationStatusUIState
}