package com.photography.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.photography.data.local.database.daos.PhotosDao
import com.photography.data.remote.client.RetrofitDataService
import com.photography.data.remote.data_source.PhotosRemoteMediator
import com.photography.ui.presentation.home.model.PhotosModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val retrofitDataService: RetrofitDataService,
    private val photosDao: PhotosDao,
    private val photosRemoteMediator: PhotosRemoteMediator
) : PhotosRepository {
    private val TAG = "PhotosRepositoryImpl"

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllPhotos(): Flow<PagingData<PhotosModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            remoteMediator = photosRemoteMediator,
            pagingSourceFactory = { photosDao.getAllPhotos() }
        ).flow
    }

    override suspend fun deleteAllPhotos() {
        photosDao.deleteAllPhotos()
    }

    override suspend fun insertAllPhotos(data: List<PhotosModel>) {
        photosDao.insertAllPhotos(photos = data)
    }
}