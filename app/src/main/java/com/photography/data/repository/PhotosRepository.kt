package com.photography.data.repository

import androidx.paging.PagingData
import com.photography.data.remote.client.ResponseEvent
import com.photography.data.remote.client.Result
import com.photography.ui.presentation.home.model.PhotosModel
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {

    fun getAllPhotos(): Flow<PagingData<PhotosModel>>

    suspend fun deleteAllPhotos()

    suspend fun insertAllPhotos(data: List<PhotosModel>)
}