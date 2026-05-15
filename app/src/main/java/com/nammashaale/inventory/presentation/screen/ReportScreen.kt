package com.nammashaale.inventory.presentation.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.nammashaale.inventory.presentation.viewmodel.ReportViewModel
import com.nammashaale.inventory.util.ReportPdfGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(viewModel: ReportViewModel, onBack: () -> Unit) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monthly Report") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Summary", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("Total assets: ${state.stats.totalAssets}")
                Text("Working assets: ${state.stats.workingAssets}")
                Text("Damaged assets: ${state.stats.damagedAssets}")
                Text("Pending repairs: ${state.stats.pendingRepairs}")
            }
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val file = ReportPdfGenerator.generate(context, state.stats, state.assets)
                        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "application/pdf"
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share monthly report"))
                    }
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Text("Export and Share PDF", Modifier.padding(start = 8.dp))
                }
            }
            item {
                Text("Assets included: ${state.assets.size}", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
