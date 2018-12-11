package com.example.sweethome.hrhelper.di

import android.app.Application
import com.example.sweethome.hrhelper.di.module.AppModule
import com.example.sweethome.hrhelper.di.module.RepositoryModule
import com.example.sweethome.hrhelper.di.module.RetrofitModule
import com.example.sweethome.hrhelper.di.module.RoomModule


class App : Application() {
    companion object {
        const val baseUrl = "https://beta-team.cft.ru/api/v1/"
        lateinit var component: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .retrofitModule(RetrofitModule(baseUrl))
            .repositoryModule(RepositoryModule())
            .roomModule(RoomModule())
            .appModule(AppModule(this))
            .build()
    }
}