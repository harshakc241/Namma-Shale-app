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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nammashaale.inventory.domain.model.AssetCondition
import com.nammashaale.inventory.presentation.viewmodel.HealthCheckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthCheckScreen(viewModel: HealthCheckViewModel, onBack: () -> Unit) {
    val assets by viewModel.assets.collectAsState()
    val selected = remember { mutableStateMapOf<Long, AssetCondition>() }
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monthly Health Check") },
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
                OutlinedTextField(notes, { notes = it }, label = { Text("Inspection notes") }, modifier = Modifier.fillMaxWidth())
            }
            items(assets, key = { it.id }) { asset ->
                Card {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(asset.name, fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AssetCondition.entries.forEach { condition ->
                                FilterChip(
                                    selected = (selected[asset.id] ?: asset.condition) == condition,
                                    onClick = { selected[asset.id] = condition },
                                    label = { Text(condition.label) }
                                )
                            }
                        }
                        Button(
                            onClick = {
                                viewModel.saveCheck(asset.id, selected[asset.id] ?: asset.condition, notes)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Check")
                        }
                    }
                }
            }
        }
    }
}
