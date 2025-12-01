package com.photography.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.photography.ui.presentation.main.MainScreen
import com.photography.ui.presentation.main.MainViewModel
import com.photography.ui.theme.PhotographyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotographyTheme {
                val uiState by viewModel.mainUiState.collectAsStateWithLifecycle()
                MainScreen(uiState = uiState)
            }
        }
    }
}