package com.photography.data.local.database.converters

import androidx.room.TypeConverter
import com.photography.ui.presentation.home.model.ProfileImage
import com.photography.utils.objectToString
import com.photography.utils.stringToObject

class ProfileImageModelConverter {
    @TypeConverter
    fun listToString(value: ProfileImage?): String? {
        return value?.objectToString()
    }

    @TypeConverter
    fun stringToList(value: String?): ProfileImage? {
        return value?.stringToObject()
    }
}