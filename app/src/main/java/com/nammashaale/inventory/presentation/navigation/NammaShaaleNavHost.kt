package com.nammashaale.inventory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nammashaale.inventory.domain.repository.InventoryRepository
import com.nammashaale.inventory.presentation.screen.AddAssetScreen
import com.nammashaale.inventory.presentation.screen.AssetDetailsScreen
import com.nammashaale.inventory.presentation.screen.DashboardScreen
import com.nammashaale.inventory.presentation.screen.HealthCheckScreen
import com.nammashaale.inventory.presentation.screen.LoginScreen
import com.nammashaale.inventory.presentation.screen.RepairRequestScreen
import com.nammashaale.inventory.presentation.screen.ReportScreen
import com.nammashaale.inventory.presentation.screen.SplashScreen
import com.nammashaale.inventory.presentation.viewmodel.AssetViewModel
import com.nammashaale.inventory.presentation.viewmodel.DashboardViewModel
import com.nammashaale.inventory.presentation.viewmodel.HealthCheckViewModel
import com.nammashaale.inventory.presentation.viewmodel.RepairViewModel
import com.nammashaale.inventory.presentation.viewmodel.ReportViewModel
import com.nammashaale.inventory.presentation.viewmodel.ViewModelFactory

@Composable
fun NammaShaaleNavHost(repository: InventoryRepository) {
    val navController = rememberNavController()
    val factory = ViewModelFactory(repository)

    NavHost(navController = navController, startDestination = Route.Splash.value) {
        composable(Route.Splash.value) {
            SplashScreen(onFinished = {
                navController.navigate(Route.Login.value) {
                    popUpTo(Route.Splash.value) { inclusive = true }
                }
            })
        }
        composable(Route.Login.value) {
            LoginScreen(onLogin = {
                navController.navigate(Route.Dashboard.value) {
                    popUpTo(Route.Login.value) { inclusive = true }
                }
            })
        }
        composable(Route.Dashboard.value) {
            val dashboardViewModel: DashboardViewModel = viewModel(factory = factory)
            DashboardScreen(
                viewModel = dashboardViewModel,
                onAddAsset = { navController.navigate(Route.AddAsset.value) },
                onAssetClick = { navController.navigate(Route.AssetDetails.create(it)) },
                onHealthCheck = { navController.navigate(Route.HealthCheck.value) },
                onRepair = { navController.navigate(Route.RepairRequests.value) },
                onReports = { navController.navigate(Route.Reports.value) }
            )
        }
        composable(Route.AddAsset.value) {
            val assetViewModel: AssetViewModel = viewModel(factory = factory)
            AddAssetScreen(viewModel = assetViewModel, onDone = { navController.popBackStack() })
        }
        composable(
            route = Route.AssetDetails.value,
            arguments = listOf(navArgument("assetId") { type = NavType.LongType })
        ) { backStackEntry ->
            val assetViewModel: AssetViewModel = viewModel(factory = factory)
            AssetDetailsScreen(
                assetId = backStackEntry.arguments?.getLong("assetId") ?: 0L,
                viewModel = assetViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Route.HealthCheck.value) {
            val healthCheckViewModel: HealthCheckViewModel = viewModel(factory = factory)
            HealthCheckScreen(viewModel = healthCheckViewModel, onBack = { navController.popBackStack() })
        }
        composable(Route.RepairRequests.value) {
            val repairViewModel: RepairViewModel = viewModel(factory = factory)
            RepairRequestScreen(viewModel = repairViewModel, onBack = { navController.popBackStack() })
        }
        composable(Route.Reports.value) {
            val reportViewModel: ReportViewModel = viewModel(factory = factory)
            ReportScreen(viewModel = reportViewModel, onBack = { navController.popBackStack() })
        }
    }
}
