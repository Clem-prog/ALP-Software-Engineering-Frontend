package com.example.alp_software_engineering_frontend.views.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_software_engineering_frontend.R
import com.example.alp_software_engineering_frontend.enums.PaymentEnum
import java.time.LocalDate
import java.time.temporal.TemporalQueries.localDate
import java.util.Date

//Room icon are changed to house icon since im not sure if a room icon exist

@Composable
fun RoomCardView(
    room_Number: String = "321",
    tenantName: String = "Vivian",
    dueDate: Int = 22,
    status: PaymentEnum,
    onClickCard: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClickCard() }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(Color(0xFF7E9D7B), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Bed Icon",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ROOM #${room_Number}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF224B37)
                )

                Text(
                    text = "Tenant: ${tenantName}",
                    fontSize = 14.sp,
                    color = Color(0xFF4A4A4A)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rent due: ${dueDate} day(s)",
                    fontSize = 14.sp,
                    color = Color(0xFF4A4A4A)
                )
            }
            // This is basically the space between the House icon and the rest of the text
            Spacer(modifier = Modifier.width(8.dp))
            when (status) {
                PaymentEnum.unpaid -> {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Information",
                        tint = Color(0xFF4A4A4A),
                        modifier = Modifier.size(24.dp)
                    )
                }

                PaymentEnum.paid -> {

                }

                PaymentEnum.pending -> {
                    Icon(
                        imageVector = Icons.Filled.AccessTime,
                        contentDescription = "Clock",
                        tint = Color(0xFFDAA520),
                        modifier = Modifier.size(24.dp)
                    )

                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF224B37))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            RoomCardView(
                room_Number = "",
                tenantName = "",
                dueDate = 22,
                status = PaymentEnum.paid
            )
        }
    }
}