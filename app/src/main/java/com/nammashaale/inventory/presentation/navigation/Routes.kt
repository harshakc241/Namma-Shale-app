package com.nammashaale.inventory.presentation.navigation

sealed class Route(val value: String) {
    data object Splash : Route("splash")
    data object Login : Route("login")
    data object Dashboard : Route("dashboard")
    data object AddAsset : Route("add_asset")
    data object AssetDetails : Route("asset_details/{assetId}") {
        fun create(assetId: Long) = "asset_details/$assetId"
    }
    data object HealthCheck : Route("health_check")
    data object RepairRequests : Route("repair_requests")
    data object Reports : Route("reports")
}
