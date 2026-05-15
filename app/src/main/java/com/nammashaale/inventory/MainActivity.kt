package com.nammashaale.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nammashaale.inventory.presentation.navigation.NammaShaaleNavHost
import com.nammashaale.inventory.presentation.theme.NammaShaaleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = (application as NammaShaaleApp).container
        setContent {
            NammaShaaleTheme {
                NammaShaaleNavHost(repository = container.repository)
            }
        }
    }
}
