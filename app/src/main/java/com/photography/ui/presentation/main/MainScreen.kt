package com.photography.ui.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.photography.R
import com.photography.domain.model.PhotoOfTheDayModel
import com.photography.ui.components.ScreenTopAppBar
import com.photography.ui.components.shimmerEffect
import com.photography.utils.toComposeColor

@Composable
fun MainScreen(uiState: MainUiState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenTopAppBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            PhotoOfTheDay(
                isLoading = uiState.isPhotoOfTheDayLoading,
                photoOfTheDayModel = uiState.photoOfTheDayModel
            )
        }
    }
}

@Composable
fun PhotoOfTheDay(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    photoOfTheDayModel: PhotoOfTheDayModel?
) {
    var imageUrl by remember(photoOfTheDayModel) {
        mutableStateOf(photoOfTheDayModel?.urls?.thumb)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .background(Color.Transparent)
            .then(modifier),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                )
            } else {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .crossfade(300) // Smooth 300ms transition
                        .build(),
                    contentDescription = "PhotoOfTheDay",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmerEffect()
                        )
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
                    },
                    onSuccess = {
                        // Once thumb loads successfully, update to raw
                        if (imageUrl == photoOfTheDayModel?.urls?.thumb) {
                            imageUrl = photoOfTheDayModel?.urls?.raw
                        }
                    }
                )

                if (photoOfTheDayModel?.urls?.thumb != null || photoOfTheDayModel?.urls?.raw != null) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(
                                photoOfTheDayModel.color?.toComposeColor() ?: colorResource(
                                    R.color.light_transparent
                                )
                            )
                            .padding(8.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = "Photo of the Day",
                            style = MaterialTheme.typography.titleLarge.copy(
                                colorResource(R.color.white),
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Explore breath taking new photos daily.",
                            style = MaterialTheme.typography.bodySmall.copy(colorResource(R.color.white))
                        )
                    }
                }
            }
        }
    }
}

