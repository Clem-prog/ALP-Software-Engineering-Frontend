package com.example.alp_software_engineering_frontend.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_software_engineering_frontend.enums.PagesEnum
import com.example.alp_software_engineering_frontend.enums.PaymentEnum
import com.example.alp_software_engineering_frontend.models.RoomModel
import com.example.alp_software_engineering_frontend.uiStates.RoomDataStatusUIState
import com.example.alp_software_engineering_frontend.uiStates.UserDataStatusUIState
import com.example.alp_software_engineering_frontend.viewModels.RoomViewModel
import com.example.alp_software_engineering_frontend.viewModels.UserViewModel
import com.example.alp_software_engineering_frontend.views.Components.RoomCardView
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminView(
    roomViewModel: RoomViewModel,
    userViewModel: UserViewModel,
    navController: NavController,
    token: String,
) {
    LaunchedEffect(token) {
        roomViewModel.getAllRooms(token)
        userViewModel.getAllUser(token)
    }

    val user = userViewModel.dataStatus
    val room = roomViewModel.dataStatus

    when (user) {
        is UserDataStatusUIState.GetAllSuccess -> {
            when (room) {
                is RoomDataStatusUIState.GetAllSuccess -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF224B37))
                            .padding(top = 40.dp)
                    ) {
                        Text(
                            text = "Rooms",
                            color = Color(0xFFF9BD02),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                        )

                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(room.roomModelData) { room ->
                                val dueLocal = room.dueDate.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                val today = LocalDate.now()
                                val daysLeft = ChronoUnit.DAYS.between(today, dueLocal).toInt()
                                val tenantName = room.occupant.name

                                RoomCardView(
                                    room_Number = room.room_number,
                                    tenantName = tenantName,
                                    dueDate = daysLeft,
                                    status = PaymentEnum.valueOf(room.paymentStatus),
                                    onClickCard = { navController.navigate("${PagesEnum.RoomInfo.name}/${room.id}")}
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AdminViewPreview() {

    MaterialTheme {
        AdminView(
            roomViewModel = viewModel(),
            userViewModel = viewModel(),
            token = "",
            navController = rememberNavController()
        )
    }
}
