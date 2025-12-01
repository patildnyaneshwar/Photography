package com.photography.ui.presentation.home.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.photography.data.remote.client.ResponseEvent
import com.photography.data.remote.client.Result
import com.photography.domain.repository.PhotosRepository
import com.photography.di.qualifiers.IoDispatcher
import com.photography.ui.presentation.home.model.PhotosModel
import com.photography.ui.presentation.home.view.HomeUiState
import com.photography.utils.ConstantUrls
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val photosRepository: PhotosRepository
) : ViewModel() {
    private val TAG = "PhotosViewModel"

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val photosPagingData: Flow<PagingData<PhotosModel>> = photosRepository.getAllPhotos().cachedIn(viewModelScope)
}