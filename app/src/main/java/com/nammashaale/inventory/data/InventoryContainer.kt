package com.nammashaale.inventory.data

import android.content.Context
import com.nammashaale.inventory.data.local.InventoryDatabase
import com.nammashaale.inventory.domain.repository.InventoryRepository

class InventoryContainer(context: Context) {
    private val database = InventoryDatabase.getDatabase(context)
    val repository: InventoryRepository = OfflineInventoryRepository(database.inventoryDao())
}
