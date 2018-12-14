package com.example.sweethome.hrhelper.data.source.database

import android.arch.persistence.room.TypeConverter
import com.example.sweethome.hrhelper.data.dto.CityDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EventConverter {

    var gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?): ArrayList<CityDto>? {
        val listType = object : TypeToken<ArrayList<CityDto>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: ArrayList<CityDto>?): String {
        val listType = object : TypeToken<ArrayList<CityDto>>() {}.type
        return gson.toJson(someObjects, listType)
    }
}