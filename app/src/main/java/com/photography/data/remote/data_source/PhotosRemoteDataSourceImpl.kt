package com.photography.data.remote.data_source

import android.util.Log
import com.photography.data.remote.client.ErrorBody
import com.photography.data.remote.client.ResponseEvent
import com.photography.data.remote.client.RetrofitDataService
import com.photography.di.qualifiers.IoDispatcher
import com.photography.ui.presentation.home.model.PhotosModel
import com.photography.utils.errorObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PhotosRemoteDataSourceImpl @Inject constructor(
    private val retrofitDataService: RetrofitDataService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : PhotosRemoteDataSource {
    private val TAG = "PhotosRemoteDataSourceI"

    override suspend fun getPhotos(
        accessKey: String,
        pageNo: Int,
        callback: (ResponseEvent) -> Unit
    ) {
        CoroutineScope(ioDispatcher).launch {
            callback(ResponseEvent.Loading)
            retrofitDataService.getPhotos(accessKey, pageNo)
                .enqueue(object : Callback<List<PhotosModel>> {
                    override fun onResponse(
                        call: Call<List<PhotosModel>>,
                        response: Response<List<PhotosModel>>
                    ) {
                        Log.d(TAG, "onResponse: $response")
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                callback(ResponseEvent.Success(response.body()!!))
                            } else {
                                Log.e(TAG, "Photos response is null")
                                callback(ResponseEvent.Failure(NullPointerException("Photos response body is null")))
                            }
                        } else {
                            val error = response.errorBody()?.errorObject<ErrorBody>()
                            Log.e(TAG, "onResponse: ${error?.errors!![0]}")
                            callback(ResponseEvent.Failure(Throwable(error.errors[0])))
                        }
                    }

                    override fun onFailure(call: Call<List<PhotosModel>>, t: Throwable) {
                        Log.e(TAG, "onFailure: %s", t)
                        callback(ResponseEvent.Failure(t))
                    }
                })

        }

    }

}