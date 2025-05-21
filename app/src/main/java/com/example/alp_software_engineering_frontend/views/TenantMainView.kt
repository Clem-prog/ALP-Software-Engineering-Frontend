package com.example.alp_software_engineering_frontend.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenantMainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Color(0xFF224B37),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF7E9D7B),
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = if (selectedItem == 0) Color.White else Color.LightGray) },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "Payment", tint = if (selectedItem == 1) Color.White else Color.LightGray) },
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile", tint = if (selectedItem == 2) Color.White else Color.LightGray) },
                    selected = selectedItem == 2,
                    onClick = { selectedItem = 2 }
                )
            }
        }
    ) { innerPadding ->
        when (selectedItem) {
            0 -> TenantHomePageView(modifier = Modifier.padding(innerPadding))
            1 -> TenantPaymentPageView(modifier = Modifier.padding(innerPadding))
            2 -> TenantProfileView(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TenantMainScreenPreview() {
    MaterialTheme {
        TenantMainScreen()
    }
}