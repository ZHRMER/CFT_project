package com.example.sweethome.hrhelper.data.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DateDto() : Parcelable {

    @SerializedName("start")
    @Expose
    var start: String? = null
    @SerializedName("end")
    @Expose
    var end: String? = null

    constructor(parcel: Parcel) : this() {
        start = parcel.readString()
        end = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(start)
        parcel.writeString(end)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateDto> {
        override fun createFromParcel(parcel: Parcel): DateDto {
            return DateDto(parcel)
        }

        override fun newArray(size: Int): Array<DateDto?> {
            return arrayOfNulls(size)
        }
    }

}