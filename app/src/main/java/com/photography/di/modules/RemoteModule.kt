package com.photography.di.modules

import com.photography.BuildConfig
import com.photography.data.remote.client.RetrofitDataService
import com.photography.utils.ConstantUrls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    // Dagger doesn't support @Inject into kotlin objects, final/const, static & private field
    @Provides
    fun provideBaseUrl() = ConstantUrls.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

//    @Singleton
//    @Provides
//    fun provideOkHttpClient(): OkHttpClient {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        return OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .build()
//    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitDataService(retrofit: Retrofit): RetrofitDataService = retrofit.create(RetrofitDataService::class.java)

}