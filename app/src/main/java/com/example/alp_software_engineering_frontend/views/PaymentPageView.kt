package com.example.alp_software_engineering_frontend.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
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
fun TenantPaymentPageView(
    paymentAmount: String = "Rp. 2.200.000",
    transferToAccount: String = "12345678 (John Kos)",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Payment Amount:",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = paymentAmount,
            color = Color(0xFFF8D74A),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Text(
            text = "Transfer to:",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = transferToAccount,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        Text(
            text = "Upload your transfer receipt",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                .clickable { /* TODO: Handle image upload */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Upload Receipt",
                tint = Color.Gray,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* TODO: Handle submit */ },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7E9D7B),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Submit", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF224B37)
@Composable
fun TenantPaymentPagePreview() {
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
            TenantPaymentPageView(modifier = Modifier.padding(innerPadding))
        }
    }
}