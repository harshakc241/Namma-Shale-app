package com.nammashaale.inventory.util

import android.content.Context
import java.io.File

object ImageFileProvider {
    fun createImageFile(context: Context): File {
        val directory = File(context.filesDir, "asset_photos").apply { mkdirs() }
        return File(directory, "asset_${System.currentTimeMillis()}.jpg")
    }
}
