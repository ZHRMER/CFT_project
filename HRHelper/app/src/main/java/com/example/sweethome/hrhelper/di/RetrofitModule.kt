package com.example.sweethome.hrhelper.di

import com.example.sweethome.hrhelper.data.EventsApi
import com.example.sweethome.hrhelper.extension.warning
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Module
class RetrofitModule(private var url: String) {
    @Inject
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Inject
    @Provides
    @Singleton
    fun providesEventsApi(): EventsApi {
        warning("EventsApi created")
        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create<EventsApi>(EventsApi::class.java)
    }
}