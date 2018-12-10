package com.example.sweethome.hrhelper.presentation.callbacks


interface Carry<T> {
    fun onSuccess(result: T)

    fun onFailure(throwable: Throwable = Throwable())
}