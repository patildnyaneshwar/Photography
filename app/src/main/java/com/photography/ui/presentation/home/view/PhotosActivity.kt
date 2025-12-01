package com.photography.ui.presentation.home.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.photography.ui.presentation.home.viewmodel.PhotosViewModel
import com.photography.ui.theme.PhotographyTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "PhotosActivity"

@AndroidEntryPoint
class PhotosActivity : ComponentActivity() {

    private val viewModel: PhotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotographyTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val photosLazyPaging = viewModel.photosPagingData.collectAsLazyPagingItems()
                HomeScreen(uiState = uiState, photosLazyPaging = photosLazyPaging)
            }
        }
    }
}


