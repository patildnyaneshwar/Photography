package com.photography.data.local.database.converters

import androidx.room.TypeConverter
import com.photography.ui.presentation.home.model.Social
import com.photography.utils.objectToString
import com.photography.utils.stringToObject

class SocialModelConverter {
    @TypeConverter
    fun listToString(value: Social?): String? {
        return value?.objectToString()
    }

    @TypeConverter
    fun stringToList(value: String?): Social? {
        return value?.stringToObject()
    }
}