package com.nammashaale.inventory.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nammashaale.inventory.domain.model.RepairStatus
import com.nammashaale.inventory.presentation.viewmodel.RepairViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairRequestScreen(viewModel: RepairViewModel, onBack: () -> Unit) {
    val repairs by viewModel.repairs.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repair Requests") },
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
            if (repairs.isEmpty()) {
                item { Text("No pending repair requests.") }
            }
            items(repairs, key = { it.id }) { request ->
                Card {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(request.problem, fontWeight = FontWeight.SemiBold)
                        Text("Asset ID: ${request.assetId}")
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            RepairStatus.entries.forEach { status ->
                                FilterChip(
                                    selected = request.status == status,
                                    onClick = { viewModel.updateStatus(request.id, status) },
                                    label = { Text(status.label) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
