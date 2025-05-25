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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_software_engineering_frontend.models.RoomModel
import com.example.alp_software_engineering_frontend.views.Components.RoomCardView
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminView(
    rooms: List<RoomModel>
) {
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
            items(rooms) { room ->
                val dueLocal = room.dueDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                val today = LocalDate.now()
                val daysLeft = ChronoUnit.DAYS.between(today, dueLocal).toInt()
                val tenantName = room.occupantId?.toString() ?: "—"

                RoomCardView(
                    room_Number = room.room_number,
                    tenantName = tenantName,
                    dueDate = daysLeft
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AdminViewPreview() {
    val futureDate = Date().let {
        Date(it.time + 25L * 24 * 60 * 60 * 1000)
    }
    val sampleRooms = List(6) {
        RoomModel(
            id = it,
            room_number = "321",
            room_type = "Standard",
            pricePerMonth = 2_200_000.0,
            dueDate = futureDate,
            paymentStatus = "PENDING",
            occupantId = it + 1
        )
    }

    MaterialTheme {
        AdminView(rooms = sampleRooms)
    }
}
