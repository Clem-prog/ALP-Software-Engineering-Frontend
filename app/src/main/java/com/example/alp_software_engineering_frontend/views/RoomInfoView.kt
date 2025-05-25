package com.example.alp_software_engineering_frontend.views

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
import java.util.*
import androidx.compose.ui.tooling.preview.Preview
import java.util.Date

@Composable
fun RoomInfoView(
    room: RoomModel,
    tenantName: String,
    receiptPainter: Painter? = null,
    onBack: () -> Unit,
    onPaymentHistory: () -> Unit,
    onRemindRent: () -> Unit,
    onApprove: () -> Unit,
    onDisapprove: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val dueDateStr = dateFormat.format(room.dueDate)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF224B37))
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back arrow
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onBack() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Spacer(Modifier.height(24.dp))

        // Room number badge
        Box(
            modifier = Modifier
                .background(Color(0xFF7E9D7B), RoundedCornerShape(8.dp))
                .padding(horizontal = 32.dp, vertical = 12.dp)
        ) {
            Text(
                text = room.room_number,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(Modifier.height(24.dp))

        // Detail cards
        listOf(
            "Tenant:" to tenantName,
            "Rent Due:" to dueDateStr,
            "Room Type:" to room.room_type,
            "Payment Status:" to room.paymentStatus
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

        // if proof is submitted (status == "Pending"), show receipt and approve/disapprove
        if (room.paymentStatus.equals("Pending", ignoreCase = true) && receiptPainter != null) {
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
                androidx.compose.foundation.Image(
                    painter = receiptPainter,
                    contentDescription = "Receipt Image",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onDisapprove,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Disapprove", fontSize = 16.sp, color = Color.White)
                }
                Button(
                    onClick = onApprove,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E9D7B))
                ) {
                    Text("Approve", fontSize = 16.sp, color = Color.White)
                }
            }
        } else {
            // otherwise show Payment History & Remind Rent
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onPaymentHistory,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E9D7B))
                ) {
                    Text("Payment History", fontSize = 16.sp, color = Color.White)
                }
                Button(
                    onClick = onRemindRent,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9BD02))
                ) {
                    Text("Remind rent", fontSize = 16.sp, color = Color.White)
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
        occupantId = 42
    )
    RoomInfoView(
        room = room,
        tenantName = "Igny Romy",
        receiptPainter = null,
        onBack = {},
        onPaymentHistory = {},
        onRemindRent = {},
        onApprove = {},      // won't be shown in this preview
        onDisapprove = {}    // won't be shown in this preview
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF224B37, name = "RoomInfo Pending")
@Composable
fun RoomInfoViewPreview_Pending() {
    // sample RoomModel with pending status
    val futureDate = Date(System.currentTimeMillis() + 5L * 24 * 60 * 60 * 1000) // in 5 days
    val room = RoomModel(
        id = 1,
        room_number = "321",
        room_type = "Big",
        pricePerMonth = 2_200_000.0,
        dueDate = futureDate,
        paymentStatus = "Pending",
        occupantId = 42
    )
    RoomInfoView(
        room = room,
        tenantName = "Igny Romy",
        // placeholder receipt—replace with your actual drawable
        receiptPainter = painterResource(id = R.drawable.baseline_wallet_24),
        onBack = {},
        onPaymentHistory = {},  // won't be shown in this preview
        onRemindRent = {},      // won't be shown in this preview
        onApprove = {},
        onDisapprove = {}
    )
}
