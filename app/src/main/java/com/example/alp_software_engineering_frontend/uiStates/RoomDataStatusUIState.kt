package com.example.alp_software_engineering_frontend.uiStates

import com.example.alp_software_engineering_frontend.models.RoomModel

interface RoomDataStatusUIState {
    data class GetAllSuccess(val roomModelData: List<RoomModel>): RoomDataStatusUIState
    data class Success(val roomModelData: RoomModel): RoomDataStatusUIState
    data class Updated(val data: String): RoomDataStatusUIState
    object Start: RoomDataStatusUIState
    object Loading: RoomDataStatusUIState
    data class Failed(val errorMessage: String): RoomDataStatusUIState
}