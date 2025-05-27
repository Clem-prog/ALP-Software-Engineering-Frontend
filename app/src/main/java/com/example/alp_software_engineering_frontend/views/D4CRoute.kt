package com.example.alp_software_engineering_frontend.views

import LoginView
import android.annotation.SuppressLint
import android.graphics.pdf.PdfDocument.Page
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alp_software_engineering_frontend.enums.PagesEnum
import com.example.alp_software_engineering_frontend.enums.PaymentEnum
import com.example.alp_software_engineering_frontend.models.UserModel
import com.example.alp_software_engineering_frontend.uiStates.AuthenticationUIState
import com.example.alp_software_engineering_frontend.viewModels.AuthenticationViewModel
import com.example.alp_software_engineering_frontend.viewModels.PaymentFormViewModel
import com.example.alp_software_engineering_frontend.viewModels.PaymentViewModel
import com.example.alp_software_engineering_frontend.viewModels.RoomViewModel
import com.example.alp_software_engineering_frontend.viewModels.UserViewModel

@SuppressLint("NewApi")
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

        composable(route = PagesEnum.HomeAdmin.name) {
            AdminView(
                roomViewModel = roomViewModel,
                userViewModel = userViewModel,
                token = token.value,
                navController = navController
            )
        }
        
        composable(
            route = "${PagesEnum.RoomInfo.name}/{roomId}/{status}",
            arguments = listOf(
                navArgument("roomId") { type = NavType.IntType },
                navArgument("status") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("roomId")
            val status = backStackEntry.arguments?.getString("status")
            RoomInfoView(
                roomId = id!!,
                token = token.value,
                status = PaymentEnum.valueOf(status!!),
                roomViewModel = roomViewModel,
                navController = navController,
            )
        }

        composable(route = PagesEnum.HomeTenant.name) {
            ScaffoldMain(
                navController = navController,
                content = {
                    TenantHomeView(
                        token = token.value,
                        userId = userId.value,
                        userViewModel = userViewModel,
                        roomViewModel = roomViewModel
                    )
                },
            )
        }

        composable(route = PagesEnum.Payment.name) {
            ScaffoldMain(
                navController = navController,
                content = {
                    TenantPaymentPageView(
                        roomViewModel = roomViewModel,
                        token = token.value,
                        transferToAccount = "12345678 (John Kos)"
                    )
                },
            )
        }

        composable(route = PagesEnum.Profile.name) {
            ScaffoldMain(
                navController = navController,
                content = {
                    TenantProfileView(
                        token = token.value,
                        userId = userId.value  ,
                        userViewModel = userViewModel,
                        roomViewModel = roomViewModel
                    )
                },
            )
        }
    }
}

@Composable
fun ScaffoldMain(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MaterialTheme {
        Scaffold(
            containerColor = Color(0xFF224B37),
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF7E9D7B)) {
                    NavigationBarItem(
                        selected = currentRoute == PagesEnum.HomeTenant.name,
                        onClick = {
                            if (currentRoute != PagesEnum.HomeTenant.name) {
                                navController.navigate(PagesEnum.HomeTenant.name) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == PagesEnum.Payment.name,
                        onClick = {
                            if (currentRoute != PagesEnum.Payment.name) {
                                navController.navigate(PagesEnum.Payment.name) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.List, contentDescription = "Payments") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == PagesEnum.Profile.name,
                        onClick = {
                            if (currentRoute != PagesEnum.Profile.name) {
                                navController.navigate(PagesEnum.Profile.name) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") }
                    )
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}
