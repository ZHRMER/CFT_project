package com.example.sweethome.hrhelper.presentation.screens.member.presenter

import android.support.v7.app.AppCompatActivity

class MemberInfoPresenter(private var myActivity: AppCompatActivity?) {

    fun attach(activity: AppCompatActivity?) {
        myActivity = activity
    }

    fun detach() {
        myActivity = null
    }
}