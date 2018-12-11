package com.example.sweethome.hrhelper.data.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MemberDto() : Parcelable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = "No"
    @SerializedName("city")
    @Expose
    var city: String? = "No"
    @SerializedName("company")
    @Expose
    var company: String? = "No"
    @SerializedName("position")
    @Expose
    var position: String? = "No"
    @SerializedName("addition")
    @Expose
    var addition: String? = "No"
    @SerializedName("registeredDate")
    @Expose
    var registeredDate: String? = "No"
    @SerializedName("agreedByManager")
    @Expose
    var agreedByManager: String? = "No"
    @SerializedName("lastName")
    @Expose
    var lastName: String? = "No"
    @SerializedName("firstName")
    @Expose
    var firstName: String? = "No"
    @SerializedName("patronymic")
    @Expose
    var patronymic: String? = "No"
    @SerializedName("email")
    @Expose
    var email: String? = "No"
    var isArrived: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        phone = parcel.readString()
        city = parcel.readString()
        company = parcel.readString()
        position = parcel.readString()
        addition = parcel.readString()
        registeredDate = parcel.readString()
        agreedByManager = parcel.readString()
        lastName = parcel.readString()
        firstName = parcel.readString()
        patronymic = parcel.readString()
        email = parcel.readString()
        isArrived = parcel.readString()!!.toBoolean()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(phone)
        parcel.writeString(city)
        parcel.writeString(company)
        parcel.writeString(position)
        parcel.writeString(addition)
        parcel.writeString(registeredDate)
        parcel.writeString(agreedByManager)
        parcel.writeString(lastName)
        parcel.writeString(firstName)
        parcel.writeString(patronymic)
        parcel.writeString(email)
        parcel.writeString(isArrived.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemberDto> {
        override fun createFromParcel(parcel: Parcel): MemberDto {
            return MemberDto(parcel)
        }

        override fun newArray(size: Int): Array<MemberDto?> {
            return arrayOfNulls(size)
        }
    }

}