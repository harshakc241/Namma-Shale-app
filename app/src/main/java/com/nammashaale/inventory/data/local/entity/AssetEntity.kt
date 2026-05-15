package com.nammashaale.inventory.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nammashaale.inventory.domain.model.AssetCondition

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,
    val serialNumber: String,
    val condition: AssetCondition,
    val imageUri: String?,
    val purchaseDateMillis: Long,
    val quantity: Int,
    val location: String,
    val createdAtMillis: Long = System.currentTimeMillis()
)
