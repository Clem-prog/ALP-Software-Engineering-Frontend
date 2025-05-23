package com.example.alp_software_engineering_frontend.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.alp_software_engineering_frontend.repositories.UserRepository
import com.example.alp_software_engineering_frontend.uiStates.UserDataStatusUIState

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var dataStatus: UserDataStatusUIState by mutableStateOf(UserDataStatusUIState.Start)
        private set
}