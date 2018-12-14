package com.example.sweethome.hrhelper.di.module

import com.example.sweethome.hrhelper.data.source.network.EventsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Inject
    @Provides
    @Singleton
    fun providesEventsApi(retrofit: Retrofit): EventsApi {
        return retrofit.create<EventsApi>(EventsApi::class.java)
    }
}