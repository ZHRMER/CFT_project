package com.example.sweethome.hrhelper.common_models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Date {

    @SerializedName("start")
    @Expose
    var start: String? = null
    @SerializedName("end")
    @Expose
    var end: String? = null

}