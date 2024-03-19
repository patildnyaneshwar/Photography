package com.photography.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.photography.ui.presentation.home.model.PhotosModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {

    @Query("SELECT * FROM PhotosModel")
    fun getAllPhotos(): Flow<List<PhotosModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photos: List<PhotosModel>)

    @Query("DELETE FROM PhotosModel")
    suspend fun deleteAllPhotos()
}