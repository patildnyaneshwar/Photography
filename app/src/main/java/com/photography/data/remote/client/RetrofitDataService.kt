package com.photography.data.remote.client

import com.photography.domain.model.PhotoOfTheDayModel
import com.photography.ui.presentation.home.model.PhotosModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitDataService {

    @GET("photos/random")
    fun getPhotoOfTheDay(): Call<PhotoOfTheDayModel>

    @GET("photos")
    suspend fun getPhotosList(
        @Query("page") pageNo: Int
    ): List<PhotosModel>



}