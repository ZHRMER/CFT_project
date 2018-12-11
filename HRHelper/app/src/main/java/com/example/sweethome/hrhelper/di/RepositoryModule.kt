package com.example.sweethome.hrhelper.di

import com.example.sweethome.hrhelper.data.repository.EventRepository
import com.example.sweethome.hrhelper.extension.warning
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Inject
    @Provides
    @Singleton
    fun providesEventRepository(): EventRepository {
        warning("EventRepository created")
        return EventRepository()
    }
}