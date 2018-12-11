package com.example.sweethome.hrhelper.data.dto

import android.arch.persistence.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "event")
class EventDto(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "event_id")
    var id: Int? = 0,
    @SerializedName("title")
    @Expose
    var title: String? = "",

    @SerializedName("cities")
    @Expose
    @TypeConverters(TypeConverter::class)
    var cities: ArrayList<CityDto>? = ArrayList(),

    @SerializedName("description")
    @Expose
    var description: String? = "",
    @SerializedName("format")
    @Expose
    var format: Int? = 0,
    @Embedded
    @SerializedName("date")
    @Expose
    var date: DateDto? = DateDto(),
    @SerializedName("cardImage")
    @Expose
    var cardImage: String? = "",
    @SerializedName("status")
    @Expose
    var status: Int? = 0,
    @SerializedName("iconStatus")
    @Expose
    var iconStatus: String? = "",
    @SerializedName("eventFormat")
    @Expose
    var eventFormat: String? = "",
    @SerializedName("eventFormatEng")
    @Expose
    var eventFormatEng: String? = ""
) {
    @Ignore
    constructor() : this(0, "", ArrayList(), "", 0, DateDto(), "", 0, "", "", "")
}