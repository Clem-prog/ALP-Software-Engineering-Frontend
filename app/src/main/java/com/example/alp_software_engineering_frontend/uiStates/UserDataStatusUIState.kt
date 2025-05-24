package com.example.alp_software_engineering_frontend.uiStates

import com.example.alp_software_engineering_frontend.models.UserModel

interface UserDataStatusUIState {
    data class GetAllSuccess(val userModelData: List<UserModel>): UserDataStatusUIState
    data class Success(val userModelData: UserModel): UserDataStatusUIState
    object Start: UserDataStatusUIState
    object Loading: UserDataStatusUIState
    data class Failed(val errorMessage: String): UserDataStatusUIState
}