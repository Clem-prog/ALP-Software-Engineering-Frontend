package com.example.alp_software_engineering_frontend.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alp_software_engineering_frontend.uiStates.RoomDataStatusUIState
import com.example.alp_software_engineering_frontend.uiStates.UserDataStatusUIState
import com.example.alp_software_engineering_frontend.viewModels.RoomViewModel
import com.example.alp_software_engineering_frontend.viewModels.UserViewModel

@Composable
fun TenantProfileView(
    token: String,
    userId: Int,
    userViewModel: UserViewModel,
    roomViewModel: RoomViewModel
) {
    if (token.isNotEmpty()) {
        LaunchedEffect(token) {
            userViewModel.getUserById(token, userId)
            roomViewModel.getRoomByOccupant(token)
        }
    }

    val dataStatus = userViewModel.dataStatus
    val room = roomViewModel.dataStatus

    when (dataStatus) {
        is UserDataStatusUIState.Success -> {
            when (room) {
                is RoomDataStatusUIState.Success -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp, start = 20.dp, end = 20.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .padding(24.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Tenant Profile",
                                fontSize = 30.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF0C3A2D),
                                modifier = Modifier.padding(bottom = 24.dp)
                            )

                            ProfileDetailItem(label = "Name", value = dataStatus.userModelData.name)
                            ProfileDetailItem(
                                label = "Room Number",
                                value = room.roomModelData.room_number
                            )
                            ProfileDetailItem(
                                label = "Email",
                                value = dataStatus.userModelData.email
                            )
                            ProfileDetailItem(
                                label = "Phone Number",
                                value = dataStatus.userModelData.phone_number
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                ProfileDetailItem(
                                    label = "Age",
                                    value = dataStatus.userModelData.age,
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileDetailItem(
                                    label = "Gender",
                                    value = dataStatus.userModelData.gender,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                is RoomDataStatusUIState.Failed -> {
                    Text("Failed to load data: ${room.errorMessage}")
                }
            }
        }
        is UserDataStatusUIState.Failed -> {
            Text("Failed to load user: ${dataStatus.errorMessage}")
        }
    }
}

@Composable
fun ProfileDetailItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFF0C3A2D)
        )
        Text(
            text = value,
            fontSize = 20.sp,
            color = Color(0xFF0C3A2D),
            fontWeight = FontWeight(400)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TenantProfilePreview() {
    MaterialTheme {
        Scaffold(
            containerColor = Color(0xFF224B37),
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF7E9D7B)) {
                    NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Home, "")})
                    NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.List, "")})
                    NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.AccountCircle, "")})
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {  }
            TenantProfileView(
                token = "",
                userId = 0,
                userViewModel = viewModel(),
                roomViewModel = viewModel()
            )
        }
    }
}