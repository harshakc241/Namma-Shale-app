package com.nammashaale.inventory.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nammashaale.inventory.presentation.component.AssetRow
import com.nammashaale.inventory.presentation.component.InventoryIcons
import com.nammashaale.inventory.presentation.component.StatCard
import com.nammashaale.inventory.presentation.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onAddAsset: () -> Unit,
    onAssetClick: (Long) -> Unit,
    onHealthCheck: () -> Unit,
    onRepair: () -> Unit,
    onReports: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("School Asset Dashboard") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAsset) {
                Icon(Icons.Default.Add, contentDescription = "Add asset")
            }
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
                Text("Namma-Shaale Inventory", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("Track, audit, repair, and report school assets.", color = MaterialTheme.colorScheme.secondary)
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        StatCard("Total", state.stats.totalAssets, InventoryIcons.Total, MaterialTheme.colorScheme.primary, Modifier.weight(1f))
                        StatCard("Working", state.stats.workingAssets, InventoryIcons.Working, Color(0xFF1B8A5A), Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        StatCard("Damaged", state.stats.damagedAssets, InventoryIcons.Damaged, Color(0xFFD64545), Modifier.weight(1f))
                        StatCard("Repairs", state.stats.pendingRepairs, InventoryIcons.Repair, Color(0xFFF2A900), Modifier.weight(1f))
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    FilledTonalButton(onClick = onHealthCheck, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.AssignmentTurnedIn, contentDescription = null)
                        Text("Health", Modifier.padding(start = 6.dp))
                    }
                    FilledTonalButton(onClick = onRepair, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.SettingsSuggest, contentDescription = null)
                        Text("Repair", Modifier.padding(start = 6.dp))
                    }
                    Button(onClick = onReports, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.PictureAsPdf, contentDescription = null)
                        Text("Report", Modifier.padding(start = 6.dp))
                    }
                }
            }
            item {
                Text("Recent Assets", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }
            if (state.assets.isEmpty()) {
                item {
                    Text("No assets yet. Tap + to register the first item.", color = MaterialTheme.colorScheme.secondary)
                }
            } else {
                items(state.assets, key = { it.id }) { asset ->
                    AssetRow(
                        name = asset.name,
                        category = asset.category,
                        location = asset.location,
                        condition = asset.condition,
                        onClick = { onAssetClick(asset.id) }
                    )
                }
            }
        }
    }
}
