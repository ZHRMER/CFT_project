package com.example.sweethome.hrhelper.data.source.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ServerResponse {
    @SerializedName("result")
    @Expose
    var result: Boolean? = false
    @SerializedName("message")
    @Expose
    var message: String? = ""
}