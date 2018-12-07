package com.example.sweethome.hrhelper.common_models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Event() :Parcelable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("cities")
    @Expose
    var cities: List<City>? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("format")
    @Expose
    var format: Int? = null
    @SerializedName("date")
    @Expose
    var date: Date? = null
    @SerializedName("cardImage")
    @Expose
    var cardImage: String? = null
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("iconStatus")
    @Expose
    var iconStatus: String? = null
    @SerializedName("eventFormat")
    @Expose
    var eventFormat: String? = null
    @SerializedName("eventFormatEng")
    @Expose
    var eventFormatEng: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        title = parcel.readString()
        description = parcel.readString()
        format = parcel.readValue(Int::class.java.classLoader) as? Int
        cardImage = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        iconStatus = parcel.readString()
        eventFormat = parcel.readString()
        eventFormatEng = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeValue(format)
        parcel.writeString(cardImage)
        parcel.writeValue(status)
        parcel.writeString(iconStatus)
        parcel.writeString(eventFormat)
        parcel.writeString(eventFormatEng)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}