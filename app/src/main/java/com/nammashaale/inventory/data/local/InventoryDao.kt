package com.nammashaale.inventory.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nammashaale.inventory.data.local.entity.AssetEntity
import com.nammashaale.inventory.data.local.entity.HealthCheckEntity
import com.nammashaale.inventory.data.local.entity.IssueEntity
import com.nammashaale.inventory.data.local.entity.RepairRequestEntity
import com.nammashaale.inventory.domain.model.AssetCondition
import com.nammashaale.inventory.domain.model.RepairStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Query("SELECT * FROM assets ORDER BY createdAtMillis DESC")
    fun observeAssets(): Flow<List<AssetEntity>>

    @Query("SELECT * FROM assets WHERE id = :id")
    fun observeAsset(id: Long): Flow<AssetEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: AssetEntity): Long

    @Update
    suspend fun updateAsset(asset: AssetEntity)

    @Query("UPDATE assets SET condition = :condition WHERE id = :assetId")
    suspend fun updateAssetCondition(assetId: Long, condition: AssetCondition)

    @Insert
    suspend fun insertHealthCheck(check: HealthCheckEntity): Long

    @Query("SELECT * FROM health_checks WHERE assetId = :assetId ORDER BY checkedAtMillis DESC")
    fun observeHealthChecks(assetId: Long): Flow<List<HealthCheckEntity>>

    @Insert
    suspend fun insertIssue(issue: IssueEntity): Long

    @Query("SELECT * FROM issues ORDER BY createdAtMillis DESC")
    fun observeIssues(): Flow<List<IssueEntity>>

    @Insert
    suspend fun insertRepairRequest(request: RepairRequestEntity): Long

    @Query("SELECT * FROM repair_requests ORDER BY requestedAtMillis DESC")
    fun observeRepairRequests(): Flow<List<RepairRequestEntity>>

    @Query("UPDATE repair_requests SET status = :status WHERE id = :requestId")
    suspend fun updateRepairStatus(requestId: Long, status: RepairStatus)

    @Query("SELECT COUNT(*) FROM assets")
    fun observeTotalAssets(): Flow<Int>

    @Query("SELECT COUNT(*) FROM assets WHERE condition = :condition")
    fun observeAssetCountByCondition(condition: AssetCondition): Flow<Int>

    @Query("SELECT COUNT(*) FROM repair_requests WHERE status != 'COMPLETED'")
    fun observePendingRepairCount(): Flow<Int>
}
