package com.example.sweethome.hrhelper.di

import android.content.Context
import com.example.sweethome.hrhelper.data.repository.CommonRepository
import com.example.sweethome.hrhelper.data.source.database.AppDatabase
import com.example.sweethome.hrhelper.data.source.network.EventsApi
import com.example.sweethome.hrhelper.di.module.AppModule
import com.example.sweethome.hrhelper.di.module.RepositoryModule
import com.example.sweethome.hrhelper.di.module.RetrofitModule
import com.example.sweethome.hrhelper.di.module.RoomModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, RoomModule::class, RepositoryModule::class])
interface AppComponent {
    fun createEventRepository(): CommonRepository
    fun createEventApi(): EventsApi
    fun createRetrofit(): Retrofit
    fun createRoom(): AppDatabase
    fun createContext(): Context
}