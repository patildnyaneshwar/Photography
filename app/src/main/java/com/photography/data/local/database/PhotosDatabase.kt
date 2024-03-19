package com.photography.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.photography.data.local.database.converters.LinksModelConverter
import com.photography.data.local.database.converters.ProfileImageModelConverter
import com.photography.data.local.database.converters.SocialModelConverter
import com.photography.data.local.database.converters.StringListConverter
import com.photography.data.local.database.converters.SponsorshipModelConverter
import com.photography.data.local.database.converters.UrlsModelConverter
import com.photography.data.local.database.converters.UserModelConverter
import com.photography.data.local.database.daos.PhotosDao
import com.photography.ui.presentation.home.model.PhotosModel

@Database(entities = [PhotosModel::class], version = 1, exportSchema = false)
@TypeConverters(
    StringListConverter::class,
    SponsorshipModelConverter::class,
    UserModelConverter::class,
    UrlsModelConverter::class,
    LinksModelConverter::class,
    ProfileImageModelConverter::class,
    SocialModelConverter::class
)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}