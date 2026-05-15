package com.nammashaale.inventory.util

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.nammashaale.inventory.data.local.entity.AssetEntity
import com.nammashaale.inventory.domain.repository.DashboardStats
import java.io.File
import java.io.FileOutputStream

object ReportPdfGenerator {
    fun generate(context: Context, stats: DashboardStats, assets: List<AssetEntity>): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val titlePaint = Paint().apply {
            textSize = 20f
            isFakeBoldText = true
        }
        val bodyPaint = Paint().apply { textSize = 13f }

        var y = 48f
        canvas.drawText("Namma-Shaale Inventory Monthly Report", 36f, y, titlePaint)
        y += 36f
        canvas.drawText("Total assets: ${stats.totalAssets}", 36f, y, bodyPaint)
        y += 22f
        canvas.drawText("Working assets: ${stats.workingAssets}", 36f, y, bodyPaint)
        y += 22f
        canvas.drawText("Damaged assets: ${stats.damagedAssets}", 36f, y, bodyPaint)
        y += 22f
        canvas.drawText("Pending repairs: ${stats.pendingRepairs}", 36f, y, bodyPaint)
        y += 36f
        canvas.drawText("Asset Register", 36f, y, titlePaint)
        y += 26f

        assets.take(24).forEachIndexed { index, asset ->
            val line = "${index + 1}. ${asset.name} | ${asset.category} | ${asset.condition.label} | Qty ${asset.quantity} | ${asset.location}"
            canvas.drawText(line.take(90), 36f, y, bodyPaint)
            y += 20f
        }

        document.finishPage(page)
        val directory = File(context.cacheDir, "reports").apply { mkdirs() }
        val file = File(directory, "namma_shaale_monthly_report.pdf")
        FileOutputStream(file).use { document.writeTo(it) }
        document.close()
        return file
    }
}
