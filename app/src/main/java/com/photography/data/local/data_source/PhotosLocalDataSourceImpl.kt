package com.photography.data.local.data_source

import com.photography.data.local.database.daos.PhotosDao
import com.photography.ui.presentation.home.model.PhotosModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosLocalDataSourceImpl @Inject constructor(
    private val photosDao: PhotosDao
) : PhotosLocalDataSource {

    override fun getAllPhotos(): Flow<List<PhotosModel>> {
        return photosDao.getAllPhotos()
    }

    override suspend fun deleteAllPhotos() {
        photosDao.deleteAllPhotos()
    }

    override suspend fun insertAllPhotos(data: List<PhotosModel>) {
        photosDao.insertAllPhotos(data)
    }

}