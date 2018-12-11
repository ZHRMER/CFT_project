package com.example.sweethome.hrhelper.di

import android.arch.persistence.room.Room
import android.content.Context
import com.example.sweethome.hrhelper.data.data_base_source.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }
}