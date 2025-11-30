package com.photography.data.remote.data_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.photography.data.local.database.daos.PhotosDao
import com.photography.data.remote.client.RetrofitDataService
import com.photography.ui.presentation.home.model.PhotosModel
import com.photography.utils.ConstantUrls
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator @Inject constructor(
    private val photosDao: PhotosDao,
    private val retrofitDataService: RetrofitDataService
) : RemoteMediator<Int, PhotosModel>() {
    private val TAG = "PhotosRemoteMediator"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotosModel>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // Get the maximum page number from database + 1
                    val maxPage = photosDao.getMaxPage() ?: -1
                    maxPage + 1
                }
            }

            val localItemCount = photosDao.getItemCountForPage(page)

            if (localItemCount > 0 && loadType != LoadType.REFRESH) {
                // Data exists locally, no need to fetch from API
                return MediatorResult.Success(endOfPaginationReached = false)
            }

            Log.d(TAG, "No of times  PhotosRemoteMediator called")
            val items = retrofitDataService.getPhotosList(
                accessKey = ConstantUrls.ACCESS_KEY,
                pageNo = page
            )
            val endOfPaginationReached = items.isEmpty()
            if (loadType == LoadType.REFRESH) {
                photosDao.deleteAllPhotos()
            }

            photosDao.insertAllPhotos(photos = items.map { it.copy(page = page) })

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ", e)
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ", e)
            MediatorResult.Error(e)
        }
    }
}