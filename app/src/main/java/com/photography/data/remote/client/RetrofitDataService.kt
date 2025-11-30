package com.photography.data.remote.client

import com.photography.ui.presentation.home.model.PhotosModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitDataService {

    @GET("photos")
    fun getPhotos(
        @Query("client_id") accessKey: String,
        @Query("page") pageNo: Int
    ): Call<List<PhotosModel>>

    @GET("photos")
    suspend fun getPhotosList(
        @Query("client_id") accessKey: String,
        @Query("page") pageNo: Int
    ): List<PhotosModel>



}