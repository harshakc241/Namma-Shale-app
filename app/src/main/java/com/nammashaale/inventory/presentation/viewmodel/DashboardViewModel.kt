package com.nammashaale.inventory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammashaale.inventory.data.local.entity.AssetEntity
import com.nammashaale.inventory.domain.repository.DashboardStats
import com.nammashaale.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class DashboardUiState(
    val stats: DashboardStats = DashboardStats(),
    val assets: List<AssetEntity> = emptyList()
)

class DashboardViewModel(repository: InventoryRepository) : ViewModel() {
    val uiState: StateFlow<DashboardUiState> =
        combine(repository.observeDashboardStats(), repository.observeAssets()) { stats, assets ->
            DashboardUiState(stats = stats, assets = assets)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState()
        )
}
