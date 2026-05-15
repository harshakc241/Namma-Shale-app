package com.nammashaale.inventory.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nammashaale.inventory.domain.model.RepairStatus

@Entity(
    tableName = "repair_requests",
    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            parentColumns = ["id"],
            childColumns = ["assetId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("assetId")]
)
data class RepairRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val assetId: Long,
    val problem: String,
    val status: RepairStatus = RepairStatus.PENDING,
    val requestedAtMillis: Long = System.currentTimeMillis()
)
