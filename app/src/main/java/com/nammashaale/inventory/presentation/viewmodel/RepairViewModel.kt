package com.nammashaale.inventory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammashaale.inventory.data.local.entity.RepairRequestEntity
import com.nammashaale.inventory.domain.model.RepairStatus
import com.nammashaale.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RepairViewModel(
    private val repository: InventoryRepository
) : ViewModel() {
    val repairs: StateFlow<List<RepairRequestEntity>> = repository.observeRepairRequests()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun updateStatus(requestId: Long, status: RepairStatus) {
        viewModelScope.launch {
            repository.updateRepairStatus(requestId, status)
        }
    }
}
