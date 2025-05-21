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

@Composable
fun TenantProfileView(
    modifier: Modifier = Modifier,
    name: String = "Igny Romy",
    roomNumber: String = "321",
    email: String = "iromy@gmail.com",
    phoneNumber: String = "1234567890",
    age: Int = 20,
    gender: String = "Male"
) {
    Box(
        modifier = modifier
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
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF224B37),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            ProfileDetailItem(label = "Name", value = name)
            ProfileDetailItem(label = "Room Number", value = roomNumber)
            ProfileDetailItem(label = "Email", value = email)
            ProfileDetailItem(label = "Phone Number", value = phoneNumber)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProfileDetailItem(label = "Age", value = age.toString(), modifier = Modifier.weight(1f))
                ProfileDetailItem(label = "Gender", value = gender, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun ProfileDetailItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF555555)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = Color(0xFF224B37),
            fontWeight = FontWeight.Normal
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
            TenantProfileView(modifier = Modifier.padding(innerPadding))
        }
    }
}