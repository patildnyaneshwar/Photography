package com.photography.data.remote.data_source

import com.photography.data.remote.client.ResponseEvent

interface PhotosRemoteDataSource {

    suspend fun getPhotos(
        accessKey: String,
        pageNo: Int,
        callback: (ResponseEvent) -> Unit
    )


}