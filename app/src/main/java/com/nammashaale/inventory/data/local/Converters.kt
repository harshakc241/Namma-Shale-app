package com.nammashaale.inventory.data.local

import androidx.room.TypeConverter
import com.nammashaale.inventory.domain.model.AssetCondition
import com.nammashaale.inventory.domain.model.RepairStatus

class Converters {
    @TypeConverter
    fun toAssetCondition(value: String): AssetCondition = AssetCondition.valueOf(value)

    @TypeConverter
    fun fromAssetCondition(value: AssetCondition): String = value.name

    @TypeConverter
    fun toRepairStatus(value: String): RepairStatus = RepairStatus.valueOf(value)

    @TypeConverter
    fun fromRepairStatus(value: RepairStatus): String = value.name
}
