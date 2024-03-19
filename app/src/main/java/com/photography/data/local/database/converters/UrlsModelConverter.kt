package com.photography.data.local.database.converters

import androidx.room.TypeConverter
import com.photography.ui.presentation.home.model.Urls
import com.photography.utils.objectToString
import com.photography.utils.stringToObject

class UrlsModelConverter {
    @TypeConverter
    fun listToString(value: Urls?): String? {
        return value?.objectToString()
    }

    @TypeConverter
    fun stringToList(value: String?): Urls? {
        return value?.stringToObject()
    }
}