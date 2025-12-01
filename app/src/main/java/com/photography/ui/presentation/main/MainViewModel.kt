package com.photography.ui.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photography.data.remote.client.Result
import com.photography.di.qualifiers.IoDispatcher
import com.photography.domain.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val photosRepository: PhotosRepository
) : ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()

    init {
        viewModelScope.launch(context = ioDispatcher) {
            getPhotoOfTheDay()
        }
    }

    private suspend fun getPhotoOfTheDay() {
        photosRepository.getPhotoOfTheDay().collect { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Error -> _mainUiState.update { state -> state.copy(isPhotoOfTheDayLoading = false) }

                is Result.Success -> {
                    _mainUiState.update { state ->
                        state.copy(
                            isPhotoOfTheDayLoading = false,
                            photoOfTheDayModel = result.data
                        )
                    }
                }
            }
        }
    }

}