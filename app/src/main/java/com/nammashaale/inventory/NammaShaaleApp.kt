package com.nammashaale.inventory

import android.app.Application
import com.nammashaale.inventory.data.InventoryContainer

class NammaShaaleApp : Application() {
    lateinit var container: InventoryContainer

    override fun onCreate() {
        super.onCreate()
        container = InventoryContainer(this)
    }
}
