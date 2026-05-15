package com.nammashaale.inventory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nammashaale.inventory.domain.repository.InventoryRepository

class ViewModelFactory(
    private val repository: InventoryRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(repository) as T
            modelClass.isAssignableFrom(AssetViewModel::class.java) -> AssetViewModel(repository) as T
            modelClass.isAssignableFrom(HealthCheckViewModel::class.java) -> HealthCheckViewModel(repository) as T
            modelClass.isAssignableFrom(RepairViewModel::class.java) -> RepairViewModel(repository) as T
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> ReportViewModel(repository) as T
            else -> error("Unknown ViewModel: ${modelClass.name}")
        }
    }
}
