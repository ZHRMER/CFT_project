package com.example.sweethome.hrhelper.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

}