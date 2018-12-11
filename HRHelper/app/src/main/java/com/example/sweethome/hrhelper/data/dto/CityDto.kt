package com.example.sweethome.hrhelper.data.dto

import android.arch.persistence.room.Ignore
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("nameRus")
    @Expose
    var nameRus: String? = null,
    @SerializedName("nameEng")
    @Expose
    var nameEng: String? = null,
    @SerializedName("icon")
    @Expose
    var icon: String? = null,
    @SerializedName("isActive")
    @Expose
    var isActive: Boolean? = null
) : Parcelable {

    @Ignore
    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        nameRus = parcel.readString()
        nameEng = parcel.readString()
        icon = parcel.readString()
        isActive = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nameRus)
        parcel.writeString(nameEng)
        parcel.writeString(icon)
        parcel.writeValue(isActive)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityDto> {
        override fun createFromParcel(parcel: Parcel): CityDto {
            return CityDto(parcel)
        }

        override fun newArray(size: Int): Array<CityDto?> {
            return arrayOfNulls(size)
        }
    }

}