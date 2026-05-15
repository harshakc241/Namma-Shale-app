package com.nammashaale.inventory.domain.repository

import com.nammashaale.inventory.data.local.entity.AssetEntity
import com.nammashaale.inventory.data.local.entity.HealthCheckEntity
import com.nammashaale.inventory.data.local.entity.IssueEntity
import com.nammashaale.inventory.data.local.entity.RepairRequestEntity
import com.nammashaale.inventory.domain.model.AssetCondition
import com.nammashaale.inventory.domain.model.RepairStatus
import kotlinx.coroutines.flow.Flow

data class DashboardStats(
    val totalAssets: Int = 0,
    val workingAssets: Int = 0,
    val damagedAssets: Int = 0,
    val pendingRepairs: Int = 0
)

interface InventoryRepository {
    fun observeAssets(): Flow<List<AssetEntity>>
    fun observeAsset(id: Long): Flow<AssetEntity?>
    fun observeHealthChecks(assetId: Long): Flow<List<HealthCheckEntity>>
    fun observeIssues(): Flow<List<IssueEntity>>
    fun observeRepairRequests(): Flow<List<RepairRequestEntity>>
    fun observeDashboardStats(): Flow<DashboardStats>
    suspend fun addAsset(asset: AssetEntity): Long
    suspend fun updateAsset(asset: AssetEntity)
    suspend fun addHealthCheck(check: HealthCheckEntity)
    suspend fun reportIssue(issue: IssueEntity)
    suspend fun createRepairRequest(request: RepairRequestEntity)
    suspend fun updateRepairStatus(requestId: Long, status: RepairStatus)
    suspend fun updateAssetCondition(assetId: Long, condition: AssetCondition)
}
