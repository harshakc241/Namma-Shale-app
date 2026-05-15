package com.nammashaale.inventory.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nammashaale.inventory.presentation.component.ConditionDot
import com.nammashaale.inventory.presentation.viewmodel.AssetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailsScreen(assetId: Long, viewModel: AssetViewModel, onBack: () -> Unit) {
    val asset by viewModel.observeAsset(assetId).collectAsState(initial = null)
    var issueTitle by remember { mutableStateOf("") }
    var issueDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asset Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val current = asset
            if (current == null) {
                Text("Asset not found")
            } else {
                Text(current.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                androidx.compose.foundation.layout.Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ConditionDot(current.condition)
                    Text(current.condition.label)
                }
                Text("Category: ${current.category}")
                Text("Serial number: ${current.serialNumber.ifBlank { "Not added" }}")
                Text("Quantity: ${current.quantity}")
                Text("Location: ${current.location}")
                Text("Report Issue", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                OutlinedTextField(issueTitle, { issueTitle = it }, label = { Text("Issue title") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(issueDescription, { issueDescription = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                Button(
                    enabled = issueTitle.isNotBlank(),
                    onClick = {
                        viewModel.reportIssue(current.id, issueTitle, issueDescription, null)
                        issueTitle = ""
                        issueDescription = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Issue")
                }
            }
        }
    }
}
