package com.example.alp_software_engineering_frontend.views.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.alp_software_engineering_frontend.R

@Composable
fun ReceiptCardView(
    date: String,
    receiptPainter: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Date: $date",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Transfer Receipt:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = receiptPainter,
                contentDescription = "Transfer receipt",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF224B37)
@Composable
fun ReceiptCardViewPreview() {
    ReceiptCardView(
        date = "17-08-1945",
        receiptPainter = painterResource(id = R.drawable.baseline_wallet_24),
        modifier = Modifier
            .padding(16.dp)
    )
}