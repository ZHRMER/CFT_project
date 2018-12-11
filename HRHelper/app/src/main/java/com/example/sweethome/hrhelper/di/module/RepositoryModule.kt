package com.example.sweethome.hrhelper.di.module

import com.example.sweethome.hrhelper.data.repository.EventRepository
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
        return EventRepository()
    }
}