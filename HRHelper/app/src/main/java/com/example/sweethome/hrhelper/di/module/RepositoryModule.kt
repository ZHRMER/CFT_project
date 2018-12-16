package com.example.sweethome.hrhelper.di.module

import com.example.sweethome.hrhelper.data.repository.EventListRepositoryImpl
import com.example.sweethome.hrhelper.data.repository.MemberListRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Inject
    @Provides
    @Singleton
    fun providesEventRepository(): EventListRepositoryImpl {
        return EventListRepositoryImpl()
    }

    @Inject
    @Provides
    @Singleton
    fun providesMemberRepository(): MemberListRepositoryImpl {
        return MemberListRepositoryImpl()
    }
}