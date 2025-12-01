package com.photography.ui.presentation.main

import com.photography.domain.model.PhotoOfTheDayModel

data class MainUiState(
    val isPhotoOfTheDayLoading: Boolean = true,
    val photoOfTheDayModel: PhotoOfTheDayModel? = null,
)
