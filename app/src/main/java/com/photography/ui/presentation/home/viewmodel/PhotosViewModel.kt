package com.photography.ui.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photography.data.remote.client.ResponseEvent
import com.photography.data.repository.PhotosRepository
import com.photography.ui.presentation.home.model.PhotosModel
import com.photography.utils.ConstantUrls
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel() {
    private val TAG = "PhotosViewModel"


    init {
        viewModelScope.launch {
            photosRepository.getAllPhotos()
        }
    }

    private val photosList = mutableListOf<PhotosModel>()
    val photosListFlow: StateFlow<ResponseEvent> = channelFlow {
        photosRepository.photosListFlow.catch {
            Log.e(TAG, "Exception: %s", it)
            trySend(ResponseEvent.Failure(it))
        }.collectLatest { event ->
            when (event) {
                is ResponseEvent.Success -> {
                    val list = event.data as List<PhotosModel>
                    photosList.addAll(list)
                    photosList.map { photosModel ->
                        photosModel.id
                    }
                    Log.d(TAG, "List: $photosList")
                    trySend(ResponseEvent.Success(photosList))
                }

                else -> {}
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ResponseEvent.Success(emptyList<PhotosModel>()))

    fun getPhotos(pageNo: Int) = viewModelScope.launch {
        photosRepository.getPhotos(accessKey = ConstantUrls.ACCESS_KEY, pageNo = pageNo)
    }

}