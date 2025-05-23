package com.example.alp_software_engineering_frontend.uiStates

interface UserDataStatusUIState {
    object Start: UserDataStatusUIState
    object Loading: UserDataStatusUIState
    data class Failed(val errorMessage: String): UserDataStatusUIState
}