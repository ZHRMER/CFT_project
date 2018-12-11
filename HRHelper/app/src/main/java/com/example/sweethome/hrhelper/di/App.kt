package com.example.sweethome.hrhelper.di

import android.app.Application


class App : Application() {
    companion object {
        const val baseUrl = "https://beta-team.cft.ru/api/v1/"
        lateinit var component: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .retrofitModule(RetrofitModule(baseUrl))
            .roomModule(RoomModule())
            .build()
    }
}