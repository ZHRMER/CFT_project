package com.example.sweethome.hrhelper.data.source.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Example {

    @SerializedName("result")
    @Expose
    var result: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null

}