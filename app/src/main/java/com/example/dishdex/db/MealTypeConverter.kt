package com.example.dishdex.db

import androidx.room.TypeConverter

class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        return attribute?.toString() ?: ""
    }

    @TypeConverter
    fun fromStringToAny(attribute: String): Any {
        return attribute
    }
}
