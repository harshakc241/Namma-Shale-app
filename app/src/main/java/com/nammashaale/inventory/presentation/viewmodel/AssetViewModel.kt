package com.nammashaale.inventory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammashaale.inventory.data.local.entity.AssetEntity
import com.nammashaale.inventory.data.local.entity.IssueEntity
import com.nammashaale.inventory.domain.model.AssetCondition
import com.nammashaale.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AssetViewModel(
    private val repository: InventoryRepository
) : ViewModel() {
    val assets: StateFlow<List<AssetEntity>> = repository.observeAssets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun observeAsset(assetId: Long) = repository.observeAsset(assetId)

    fun addAsset(
        name: String,
        category: String,
        serialNumber: String,
        condition: AssetCondition,
        imageUri: String?,
        purchaseDateMillis: Long,
        quantity: Int,
        location: String,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            repository.addAsset(
                AssetEntity(
                    name = name.trim(),
                    category = category.trim(),
                    serialNumber = serialNumber.trim(),
                    condition = condition,
                    imageUri = imageUri,
                    purchaseDateMillis = purchaseDateMillis,
                    quantity = quantity,
                    location = location.trim()
                )
            )
            onSaved()
        }
    }

    fun reportIssue(assetId: Long, title: String, description: String, photoUri: String?) {
        viewModelScope.launch {
            repository.reportIssue(
                IssueEntity(
                    assetId = assetId,
                    title = title.trim(),
                    description = description.trim(),
                    photoUri = photoUri
                )
            )
        }
    }
}
