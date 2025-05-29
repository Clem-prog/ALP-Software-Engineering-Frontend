package com.example.alp_software_engineering_frontend.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_software_engineering_frontend.R
import com.example.alp_software_engineering_frontend.views.Components.ReceiptCardView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alp_software_engineering_frontend.uiStates.PaymentDataStatusUIState
import com.example.alp_software_engineering_frontend.uiStates.RoomDataStatusUIState
import com.example.alp_software_engineering_frontend.viewModels.PaymentViewModel
import com.example.alp_software_engineering_frontend.viewModels.RoomViewModel

@Composable
fun PaymentHistoryView(
    roomViewModel: RoomViewModel,
    paymentViewModel: PaymentViewModel,
    roomId: Int,
    token: String
) {
    LaunchedEffect(token) {
        roomViewModel.getRoomById(token, roomId)
        paymentViewModel.getAllPayments(token, roomId)
    }
    
    val room = roomViewModel.dataStatus
    val payment = paymentViewModel.dataStatus
    
    when (room) {
        is RoomDataStatusUIState.Success -> {
            when (payment) {
                is PaymentDataStatusUIState.GetAllSuccess -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF224B37))
                            .padding(top = 24.dp)
                    ) {
                        Spacer(Modifier.height(24.dp))

                        // Room number badge
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
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

                        // History list
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(payment.paymentModelData) { payment ->
                                ReceiptCardView(
                                    payment = payment
                                )
                            }
                            // add bottom padding so last card isn't flush to the screen edge
                            item { Spacer(Modifier.height(24.dp)) }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF224B37)
@Composable
fun PaymentHistoryViewPreview() {
    PaymentHistoryView(
        roomViewModel = viewModel(),
        paymentViewModel = viewModel(),
        roomId = 0,
        token = ""
    )
}
