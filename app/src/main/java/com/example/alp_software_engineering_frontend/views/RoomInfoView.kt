package com.example.alp_software_engineering_frontend.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_software_engineering_frontend.models.RoomModel
import com.example.alp_software_engineering_frontend.R
import java.text.SimpleDateFormat
import coil.compose.rememberAsyncImagePainter
import java.util.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_software_engineering_frontend.enums.PagesEnum
import com.example.alp_software_engineering_frontend.enums.PaymentEnum
import com.example.alp_software_engineering_frontend.models.UserModel
import com.example.alp_software_engineering_frontend.uiStates.PaymentDataStatusUIState
import com.example.alp_software_engineering_frontend.uiStates.RoomDataStatusUIState
import com.example.alp_software_engineering_frontend.viewModels.PaymentViewModel
import com.example.alp_software_engineering_frontend.viewModels.RoomViewModel
import java.util.Date

@Composable
fun RoomInfoView(
    roomId: Int,
    token: String,
    roomViewModel: RoomViewModel,
    paymentViewModel: PaymentViewModel,
    status: PaymentEnum,
    navController: NavController,
    receiptPainter: Painter? = null
) {
    LaunchedEffect(token) {
        roomViewModel.getRoomById(token, roomId)
        paymentViewModel.getLatestPayment(token, roomId)
    }

    val room = roomViewModel.dataStatus
    val payment = paymentViewModel.dataStatus

    when (room) {
        is RoomDataStatusUIState.Success -> {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF224B37))
                    .padding(horizontal = 16.dp)
                    .padding(top = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Room number badge
                Box(
                    modifier = Modifier
                        .background(Color(0xFF7E9D7B), RoundedCornerShape(8.dp))
                        .padding(horizontal = 32.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = room.roomModelData.room_number,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Detail cards
                listOf(
                    "Tenant:" to room.roomModelData.occupant.name,
                    "Rent Due:" to formatter.format(room.roomModelData.dueDate),
                    "Room Type:" to room.roomModelData.room_type,
                    "Payment Status:" to room.roomModelData.paymentStatus
                ).forEach { (label, value) ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = value,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }

                when (status) {
                    PaymentEnum.paid -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("${PagesEnum.PaymentHistory.name}/${room.roomModelData.id}")
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E9D7B))
                            ) {
                                Text("Payment History", fontSize = 16.sp, color = Color.White)
                            }
                        }
                    }

                    PaymentEnum.unpaid -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("${PagesEnum.PaymentHistory.name}/${room.roomModelData.id}")
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E9D7B))
                            ) {
                                Text("Payment History", fontSize = 16.sp, color = Color.White)
                            }
                            Button(
                                onClick = {
                                    //remind tenant
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9BD02))
                            ) {
                                Text("Remind rent", fontSize = 16.sp, color = Color.White)
                            }
                        }
                    }

                    PaymentEnum.pending -> {
                        when (payment) {
                            is PaymentDataStatusUIState.Success -> {
                                val painter = rememberAsyncImagePainter(payment.paymentModelData.transfer_receipt)

                                Text(
                                    text = "Transfer Receipt",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(start = 8.dp, bottom = 4.dp)
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .background(Color.White, RoundedCornerShape(8.dp))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painter,
                                        contentDescription = "Receipt Image",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Text(
                                    text = "Sent in: ${formatter.format(payment.paymentModelData.date)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(top = 8.dp, bottom = 4.dp)
                                )

                                Spacer(Modifier.height(16.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = {
                                            paymentViewModel.deletePayment(token, payment.paymentModelData.id)
                                            roomViewModel.updateRoomStatus(token, room.roomModelData.id, PaymentEnum.unpaid, navController)
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                                    ) {
                                        Text("Disapprove", fontSize = 16.sp, color = Color.White)
                                    }
                                    Button(
                                        onClick = {
                                            roomViewModel.updateRoomStatus(token, room.roomModelData.id, PaymentEnum.paid, navController)
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E9D7B))
                                    ) {
                                        Text("Approve", fontSize = 16.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF224B37, name = "RoomInfo Unpaid")
@Composable
fun RoomInfoViewPreview_Unpaid() {
    // sample RoomModel with unpaid status
    val room = RoomModel(
        id = 1,
        room_number = "321",
        room_type = "Big",
        pricePerMonth = 2_200_000.0,
        dueDate = Date(),             // today
        paymentStatus = "Unpaid",
        occupantId = 42,
        occupant = UserModel(
            id = 1,
            name = "Igny Romy",
            email = "TODO()",
            password = "TODO()",
            phone_number = "TODO()",
            age = "TODO()",
            gender = "TODO()",
            isAdmin = false,
            token = "TODO()"
        )
    )
    RoomInfoView(
        receiptPainter = null,
        roomId = 0,
        token = "TODO()",
        roomViewModel = viewModel(),
        paymentViewModel = viewModel(),
        status = PaymentEnum.unpaid,
        navController = rememberNavController(),
    )
}
