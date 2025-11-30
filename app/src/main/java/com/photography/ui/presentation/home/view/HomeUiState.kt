package com.photography.ui.presentation.home.view

import com.photography.ui.presentation.home.model.PhotosModel

data class HomeUiState(val list: List<PhotosModel> = emptyList(), val isLoading: Boolean = false)
