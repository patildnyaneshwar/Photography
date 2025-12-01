package com.photography.data.remote.client

import com.photography.utils.ConstantUrls
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.method == "GET") {
            val accessKey = ConstantUrls.ACCESS_KEY

            if (!accessKey.isNullOrEmpty()) {
                val originalUrl = originalRequest.url
                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("client_id", accessKey)
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()

                return chain.proceed(newRequest)
            }
        }

        return chain.proceed(originalRequest)
    }
}