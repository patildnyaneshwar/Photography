package com.photography.ui.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.photography.ui.presentation.TopAppBar
import com.photography.ui.presentation.home.model.PhotosModel

private const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(uiState: HomeUiState, photosLazyPaging: LazyPagingItems<PhotosModel>) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                PhotosGrid(photosLazyPaging = photosLazyPaging)
            }
        })
}

@Composable
fun PhotosGrid(photosLazyPaging: LazyPagingItems<PhotosModel>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(all = 4.dp)
    ) {
        items(photosLazyPaging.itemCount) { index ->
            photosLazyPaging[index]?.let { item ->
                PhotosItem(photosModel = item)
            }
        }

        when (photosLazyPaging.loadState.append) {
            is LoadState.Loading -> {
                item {
                    LoadingItem()
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorItem(
                        message = (photosLazyPaging.loadState.append as LoadState.Error)
                            .error.message ?: "Unknown error",
                        onRetry = { photosLazyPaging.retry() }
                    )
                }
            }

            is LoadState.NotLoading -> {}
        }

        // Handle initial loading state
        if (photosLazyPaging.loadState.refresh is LoadState.Loading && photosLazyPaging.itemCount == 0) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // Handle initial error state
        if (photosLazyPaging.loadState.refresh is LoadState.Error && photosLazyPaging.itemCount == 0) {
            item {
                ErrorItem(
                    message = (photosLazyPaging.loadState.refresh as LoadState.Error)
                        .error.message ?: "Unknown error",
                    onRetry = { photosLazyPaging.retry() }
                )
            }
        }
    }
}

@Composable
fun PhotosItem(photosModel: PhotosModel) {
    val imgUrl = photosModel.urls?.regular

    SubcomposeAsyncImage(
        model = imgUrl,
        contentDescription = "Image",
        modifier = Modifier
            .height(248.dp)
            .width(200.dp),
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        error = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.BrokenImage,
                    contentDescription = "Error",
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    )
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
