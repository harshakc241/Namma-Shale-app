package com.nammashaale.inventory.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nammashaale.inventory.data.local.entity.AssetEntity
import com.nammashaale.inventory.data.local.entity.HealthCheckEntity
import com.nammashaale.inventory.data.local.entity.IssueEntity
import com.nammashaale.inventory.data.local.entity.RepairRequestEntity

@Database(
    entities = [
        AssetEntity::class,
        HealthCheckEntity::class,
        IssueEntity::class,
        RepairRequestEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao

    companion object {
        @Volatile private var instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    InventoryDatabase::class.java,
                    "namma_shaale_inventory.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
