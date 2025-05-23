package com.example.alp_software_engineering_frontend.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.alp_software_engineering_frontend.repositories.RoomRepository
import com.example.alp_software_engineering_frontend.uiStates.RoomDataStatusUIState

class RoomViewModel(
    private var roomRepository: RoomRepository
) : ViewModel() {
    var dataStatus: RoomDataStatusUIState by mutableStateOf(RoomDataStatusUIState.Start)
        private set
}