package com.example.sweethome.hrhelper.di

import com.example.sweethome.hrhelper.data.EventsApi
import com.example.sweethome.hrhelper.data.repository.EventRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, RoomModule::class, RepositoryModule::class])
interface AppComponent {
    fun createEventRepository(): EventRepository
    fun createEventApi(): EventsApi
}