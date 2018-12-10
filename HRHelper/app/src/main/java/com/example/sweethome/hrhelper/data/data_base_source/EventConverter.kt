package com.example.sweethome.hrhelper.data.data_base_source

import android.arch.persistence.room.TypeConverter
import com.example.sweethome.hrhelper.data.model.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EventConverter {

    var gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?): ArrayList<City>? {
        val listType = object : TypeToken<ArrayList<City>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: ArrayList<City>?): String {
        val listType = object : TypeToken<ArrayList<City>>() {}.type
        return gson.toJson(someObjects, listType)
    }
}