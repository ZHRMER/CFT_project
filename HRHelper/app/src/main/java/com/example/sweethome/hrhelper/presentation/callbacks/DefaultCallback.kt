package com.example.sweethome.hrhelper.presentation.callbacks

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultCallback<T>(private val carry: Carry<T>) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            carry.onSuccess(response.body()!!)
        } else {
            carry.onFailure()
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        carry.onFailure(t)
    }

}