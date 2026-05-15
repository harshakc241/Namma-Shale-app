package com.nammashaale.inventory.data

import com.nammashaale.inventory.data.local.InventoryDao
import com.nammashaale.inventory.data.local.entity.AssetEntity
import com.nammashaale.inventory.data.local.entity.HealthCheckEntity
import com.nammashaale.inventory.data.local.entity.IssueEntity
import com.nammashaale.inventory.data.local.entity.RepairRequestEntity
import com.nammashaale.inventory.domain.model.AssetCondition
import com.nammashaale.inventory.domain.model.RepairStatus
import com.nammashaale.inventory.domain.repository.DashboardStats
import com.nammashaale.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class OfflineInventoryRepository(
    private val dao: InventoryDao
) : InventoryRepository {
    override fun observeAssets(): Flow<List<AssetEntity>> = dao.observeAssets()
    override fun observeAsset(id: Long): Flow<AssetEntity?> = dao.observeAsset(id)
    override fun observeHealthChecks(assetId: Long): Flow<List<HealthCheckEntity>> = dao.observeHealthChecks(assetId)
    override fun observeIssues(): Flow<List<IssueEntity>> = dao.observeIssues()
    override fun observeRepairRequests(): Flow<List<RepairRequestEntity>> = dao.observeRepairRequests()

    override fun observeDashboardStats(): Flow<DashboardStats> {
        return combine(
            dao.observeTotalAssets(),
            dao.observeAssetCountByCondition(AssetCondition.WORKING),
            dao.observeAssetCountByCondition(AssetCondition.BROKEN),
            dao.observeAssetCountByCondition(AssetCondition.NEEDS_REPAIR),
            dao.observePendingRepairCount()
        ) { total, working, broken, needsRepair, pending ->
            DashboardStats(
                totalAssets = total,
                workingAssets = working,
                damagedAssets = broken + needsRepair,
                pendingRepairs = pending
            )
        }
    }

    override suspend fun addAsset(asset: AssetEntity): Long = dao.insertAsset(asset)
    override suspend fun updateAsset(asset: AssetEntity) = dao.updateAsset(asset)

    override suspend fun addHealthCheck(check: HealthCheckEntity) {
        dao.insertHealthCheck(check)
        dao.updateAssetCondition(check.assetId, check.condition)
        if (check.condition != AssetCondition.WORKING) {
            dao.insertRepairRequest(
                RepairRequestEntity(
                    assetId = check.assetId,
                    problem = "Repair required after health check: ${check.condition.label}"
                )
            )
        }
    }

    override suspend fun reportIssue(issue: IssueEntity) {
        dao.insertIssue(issue)
        dao.updateAssetCondition(issue.assetId, AssetCondition.NEEDS_REPAIR)
        dao.insertRepairRequest(
            RepairRequestEntity(
                assetId = issue.assetId,
                problem = issue.title
            )
        )
    }

    override suspend fun createRepairRequest(request: RepairRequestEntity) {
        dao.insertRepairRequest(request)
    }

    override suspend fun updateRepairStatus(requestId: Long, status: RepairStatus) {
        dao.updateRepairStatus(requestId, status)
    }

    override suspend fun updateAssetCondition(assetId: Long, condition: AssetCondition) {
        dao.updateAssetCondition(assetId, condition)
    }
}
