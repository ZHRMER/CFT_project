package com.example.sweethome.hrhelper.data.model

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "event")
class Event(
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
    var cities: ArrayList<City>? = ArrayList(),

    @SerializedName("description")
    @Expose
    var description: String? = "",
    @SerializedName("format")
    @Expose
    var format: Int? = 0,
    @Embedded
    @SerializedName("date")
    @Expose
    var date: Date? = Date(),
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
) : Parcelable {
    @Ignore
    constructor() : this(0, "", ArrayList(), "", 0, Date(), "", 0, "", "", "")

    @Ignore
    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        title = parcel.readString()
        cities = parcel.createTypedArrayList(City)
        description = parcel.readString()
        format = parcel.readValue(Int::class.java.classLoader) as? Int
        date = parcel.readParcelable(Date::class.java.classLoader)
        cardImage = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        iconStatus = parcel.readString()
        eventFormat = parcel.readString()
        eventFormatEng = parcel.readString()
    }

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeTypedList(cities)
        parcel.writeString(description)
        parcel.writeValue(format)
        parcel.writeParcelable(date, flags)
        parcel.writeString(cardImage)
        parcel.writeValue(status)
        parcel.writeString(iconStatus)
        parcel.writeString(eventFormat)
        parcel.writeString(eventFormatEng)
    }

    @Ignore
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        @Ignore
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        @Ignore
        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}