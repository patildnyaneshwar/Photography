package com.photography.data.repository

import com.photography.data.remote.client.ResponseEvent
import com.photography.ui.presentation.home.model.PhotosModel
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {

    val photosListFlow: Flow<ResponseEvent>

    suspend fun getPhotos(accessKey: String, pageNo: Int)

    suspend fun getAllPhotos()

    suspend fun deleteAllPhotos()

    suspend fun insertAllPhotos(data: List<PhotosModel>)
}