package com.nammashaale.inventory.domain.model

enum class AssetCondition(val label: String) {
    WORKING("Working"),
    NEEDS_REPAIR("Needs Repair"),
    BROKEN("Broken")
}

enum class RepairStatus(val label: String) {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed")
}
