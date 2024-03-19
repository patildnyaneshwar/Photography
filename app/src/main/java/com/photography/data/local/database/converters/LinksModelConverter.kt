package com.photography.data.local.database.converters

import androidx.room.TypeConverter
import com.photography.ui.presentation.home.model.Links
import com.photography.utils.objectToString
import com.photography.utils.stringToObject

class LinksModelConverter {
    @TypeConverter
    fun listToString(value: Links?): String? {
        return value?.objectToString()
    }

    @TypeConverter
    fun stringToList(value: String?): Links? {
        return value?.stringToObject()
    }
}