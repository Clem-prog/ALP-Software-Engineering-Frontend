package com.example.alp_software_engineering_frontend.uiStates

interface RoomDataStatusUIState {
    object Start: RoomDataStatusUIState
    object Loading: RoomDataStatusUIState
    data class Failed(val errorMessage: String): RoomDataStatusUIState
}