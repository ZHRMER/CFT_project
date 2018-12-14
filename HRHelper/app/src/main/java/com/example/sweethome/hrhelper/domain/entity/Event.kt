package com.example.sweethome.hrhelper.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val beginDate: Long,
    val endDate: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as Int,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Long::class.java.classLoader) as Long,
        parcel.readValue(Long::class.java.classLoader) as Long
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeValue(beginDate)
        parcel.writeValue(endDate)
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