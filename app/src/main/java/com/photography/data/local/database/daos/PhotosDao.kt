package com.photography.data.local.database.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.photography.ui.presentation.home.model.PhotosModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {

    @Query("SELECT * FROM PhotosModel ORDER BY page")
    fun getAllPhotos(): PagingSource<Int, PhotosModel>

    @Query("SELECT COUNT(*) FROM PhotosModel WHERE page = :page")
    suspend fun getItemCountForPage(page: Int): Int

    @Query("SELECT MAX(page) FROM PhotosModel")
    suspend fun getMaxPage(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photos: List<PhotosModel>)

    @Query("DELETE FROM PhotosModel")
    suspend fun deleteAllPhotos()
}