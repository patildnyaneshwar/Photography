package com.photography.data.local.database.converters

import androidx.room.TypeConverter
import com.photography.ui.presentation.home.model.User
import com.photography.utils.objectToString
import com.photography.utils.stringToObject

class UserModelConverter {
    @TypeConverter
    fun listToString(value: User?): String? {
        return value?.objectToString()
    }

    @TypeConverter
    fun stringToList(value: String?): User? {
        return value?.stringToObject()
    }
}