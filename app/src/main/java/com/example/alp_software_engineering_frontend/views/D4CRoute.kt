package com.example.alp_software_engineering_frontend.views

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_software_engineering_frontend.models.UserModel
import com.example.alp_software_engineering_frontend.viewModels.AuthenticationViewModel
import com.example.alp_software_engineering_frontend.viewModels.PaymentFormViewModel
import com.example.alp_software_engineering_frontend.viewModels.PaymentViewModel
import com.example.alp_software_engineering_frontend.viewModels.RoomViewModel
import com.example.alp_software_engineering_frontend.viewModels.UserViewModel

@Composable
fun WonderOfU(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    paymentFormViewModel: PaymentFormViewModel = viewModel(factory = PaymentFormViewModel.Factory),
    paymentViewModel: PaymentViewModel = viewModel(factory = PaymentViewModel.Factory),
    roomViewModel: RoomViewModel = viewModel(factory = RoomViewModel.Factory),
    userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)
) {

}