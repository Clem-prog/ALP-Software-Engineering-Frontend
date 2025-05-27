package com.example.alp_software_engineering_frontend.views

import LoginView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alp_software_engineering_frontend.enums.PagesEnum
import com.example.alp_software_engineering_frontend.models.UserModel
import com.example.alp_software_engineering_frontend.uiStates.AuthenticationUIState
import com.example.alp_software_engineering_frontend.viewModels.AuthenticationViewModel
import com.example.alp_software_engineering_frontend.viewModels.PaymentFormViewModel
import com.example.alp_software_engineering_frontend.viewModels.PaymentViewModel
import com.example.alp_software_engineering_frontend.viewModels.RoomViewModel
import com.example.alp_software_engineering_frontend.viewModels.UserViewModel

@Composable
fun D4C(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    paymentFormViewModel: PaymentFormViewModel = viewModel(factory = PaymentFormViewModel.Factory),
    paymentViewModel: PaymentViewModel = viewModel(factory = PaymentViewModel.Factory),
    roomViewModel: RoomViewModel = viewModel(factory = RoomViewModel.Factory),
    userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)
) {
    val localContext = LocalContext.current
    val token = userViewModel.token.collectAsState()
    val isAdmin = userViewModel.isAdmin.collectAsState()
    val userId = userViewModel.id.collectAsState()

    /*navController = navController, startDestination = if(token.value != "Unknown" && token.value != "") {
            if (isAdmin.value) {
                PagesEnum.HomeAdmin.name
            } else {
                PagesEnum.HomeTenant.name
            }
        } else {
            PagesEnum.Login.name
        }*/

    NavHost(
        navController = navController, startDestination = PagesEnum.Login.name
    ) {
        composable(route = PagesEnum.Login.name) {
            LoginView(
                navController = navController,
                context = localContext,
                authenticationViewModel = authenticationViewModel
            )
        }

        composable(route = PagesEnum.HomeTenant.name) {
            TenantMainScreen()
        }

        composable(route = PagesEnum.HomeAdmin.name) {
            //TODO:
        }
    }
}