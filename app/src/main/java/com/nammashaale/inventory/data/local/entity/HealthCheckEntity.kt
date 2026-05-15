package com.nammashaale.inventory.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nammashaale.inventory.domain.model.AssetCondition

@Entity(
    tableName = "health_checks",
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
data class HealthCheckEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val assetId: Long,
    val condition: AssetCondition,
    val notes: String,
    val checkedAtMillis: Long = System.currentTimeMillis()
)
