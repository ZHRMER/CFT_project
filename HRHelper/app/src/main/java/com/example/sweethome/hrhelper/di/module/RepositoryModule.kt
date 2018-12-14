package com.example.sweethome.hrhelper.di.module

import com.example.sweethome.hrhelper.data.repository.CommonRepository
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Inject
    @Provides
    @Singleton
    fun providesEventRepository(): CommonRepository {
        return CommonRepository()
    }
}