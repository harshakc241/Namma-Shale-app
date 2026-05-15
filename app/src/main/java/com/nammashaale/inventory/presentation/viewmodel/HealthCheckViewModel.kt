package com.nammashaale.inventory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammashaale.inventory.data.local.entity.AssetEntity
import com.nammashaale.inventory.data.local.entity.HealthCheckEntity
import com.nammashaale.inventory.domain.model.AssetCondition
import com.nammashaale.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HealthCheckViewModel(
    private val repository: InventoryRepository
) : ViewModel() {
    val assets: StateFlow<List<AssetEntity>> = repository.observeAssets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun saveCheck(assetId: Long, condition: AssetCondition, notes: String) {
        viewModelScope.launch {
            repository.addHealthCheck(
                HealthCheckEntity(
                    assetId = assetId,
                    condition = condition,
                    notes = notes.trim()
                )
            )
        }
    }
}
