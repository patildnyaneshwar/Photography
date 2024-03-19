package com.photography.data.repository

import android.util.Log
import com.photography.data.local.data_source.PhotosLocalDataSource
import com.photography.data.remote.client.ResponseEvent
import com.photography.data.remote.data_source.PhotosRemoteDataSource
import com.photography.di.qualifiers.IoDispatcher
import com.photography.ui.presentation.home.model.PhotosModel
import com.photography.utils.ConstantUrls
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val photosLocalDataSource: PhotosLocalDataSource,
    private val photosRemoteDataSource: PhotosRemoteDataSource
): PhotosRepository {
    private val TAG = "PhotosRepositoryImpl"

    private val _photosListFlow = MutableStateFlow<ResponseEvent>(ResponseEvent.Loading)
    override val photosListFlow: Flow<ResponseEvent> = _photosListFlow.asStateFlow()

    override suspend fun getPhotos(
        accessKey: String,
        pageNo: Int
    ) {
        photosRemoteDataSource.getPhotos(accessKey, pageNo) { event ->
            CoroutineScope(ioDispatcher).launch {
                _photosListFlow.update { event }
                when (event) {
                    is ResponseEvent.Success -> {
                        val list: List<PhotosModel> = event.data as List<PhotosModel>
                        photosLocalDataSource.insertAllPhotos(list)
                    }

                    else -> {

                    }
                }
            }
        }
    }

    override suspend fun getAllPhotos() {
        photosLocalDataSource.getAllPhotos().catch {
            Log.e(TAG, "getPhotos: %s", it)
            ResponseEvent.Failure(it)
        }.collectLatest { list ->
            Log.d(TAG, "getPhotos: $list")
            _photosListFlow.update { ResponseEvent.Success(list) }
            if (list.isEmpty()) {
                getPhotos(accessKey = ConstantUrls.ACCESS_KEY, pageNo = 0)
            }
        }
    }

    override suspend fun deleteAllPhotos() {
        photosLocalDataSource.deleteAllPhotos()
    }

    override suspend fun insertAllPhotos(data: List<PhotosModel>) {
        photosLocalDataSource.insertAllPhotos(data)
    }
}