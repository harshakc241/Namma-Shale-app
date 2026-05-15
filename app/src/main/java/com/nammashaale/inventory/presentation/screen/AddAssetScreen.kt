package com.nammashaale.inventory.presentation.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nammashaale.inventory.domain.model.AssetCondition
import com.nammashaale.inventory.presentation.viewmodel.AssetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssetScreen(viewModel: AssetViewModel, onDone: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Sports") }
    var serial by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    var condition by remember { mutableStateOf(AssetCondition.WORKING) }
    var imageUri by remember { mutableStateOf<String?>(null) }
    var showCamera by remember { mutableStateOf(false) }

    if (showCamera) {
        CameraCaptureScreen(
            onImageCaptured = {
                imageUri = it
                showCamera = false
            },
            onClose = { showCamera = false }
        )
        return
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Add Asset") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Asset Register", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            OutlinedTextField(name, { name = it }, label = { Text("Asset name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(category, { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(serial, { serial = it }, label = { Text("Serial number") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(location, { location = it }, label = { Text("School location / classroom") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(quantity, { quantity = it.filter { char -> char.isDigit() } }, label = { Text("Quantity") }, modifier = Modifier.fillMaxWidth())
            Text("Condition", fontWeight = FontWeight.SemiBold)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssetCondition.entries.forEach { option ->
                    FilterChip(
                        selected = condition == option,
                        onClick = { condition = option },
                        label = { Text(option.label) }
                    )
                }
            }
            OutlinedButton(onClick = { showCamera = true }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Text(if (imageUri == null) "Capture asset photo" else "Photo captured", Modifier.padding(start = 8.dp))
            }
            Button(
                enabled = name.isNotBlank() && category.isNotBlank() && location.isNotBlank(),
                onClick = {
                    viewModel.addAsset(
                        name = name,
                        category = category,
                        serialNumber = serial,
                        condition = condition,
                        imageUri = imageUri,
                        purchaseDateMillis = System.currentTimeMillis(),
                        quantity = quantity.toIntOrNull() ?: 1,
                        location = location,
                        onSaved = onDone
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Asset")
            }
        }
    }
}
