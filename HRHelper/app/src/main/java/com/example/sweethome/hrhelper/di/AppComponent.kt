package com.example.sweethome.hrhelper.di

import android.content.Context
import com.example.sweethome.hrhelper.data.data_base_source.AppDatabase
import com.example.sweethome.hrhelper.data.network_source.EventsApi
import com.example.sweethome.hrhelper.data.repository.EventRepository
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
    fun createEventRepository(): EventRepository
    fun createEventApi(): EventsApi
    fun createRetrofit(): Retrofit
    fun createRoom(): AppDatabase
    fun createContext(): Context
}