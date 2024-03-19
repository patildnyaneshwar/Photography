package com.photography.data.local.database.converters

import androidx.room.TypeConverter
import com.photography.ui.presentation.home.model.Sponsorship
import com.photography.utils.objectToString
import com.photography.utils.stringToObject

class SponsorshipModelConverter {

    @TypeConverter
    fun listToString(value: Sponsorship?): String? {
        return value?.objectToString()
    }

    @TypeConverter
    fun stringToList(value: String?): Sponsorship? {
        return value?.stringToObject()
    }

}