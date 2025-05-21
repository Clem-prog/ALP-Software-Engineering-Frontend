package com.example.alp_software_engineering_frontend.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
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

@Composable
fun TenantHomePageView(
    roomNumber: String = "321",
    tenantName: String = "Igny Romy",
    rentDueDate: String = "17-08-1945",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Room no.",
            color = Color(0xFFF8D74A),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF7E9D7B)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = roomNumber,
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        InfoCardHomePage(label = "Tenant:", value = tenantName)

        Spacer(modifier = Modifier.height(20.dp))

        InfoCardHomePage(label = "Rent Due:", value = rentDueDate)
    }
}

@Composable
fun InfoCardHomePage(label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = label,
                color = Color(0xFF555555),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                color = Color(0xFF224B37),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF224B37)
@Composable
fun TenantHomePagePreview() {
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
            TenantHomePageView(modifier = Modifier.padding(innerPadding))
        }
    }
}