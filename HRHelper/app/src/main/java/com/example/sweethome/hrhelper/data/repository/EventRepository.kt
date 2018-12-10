package com.example.sweethome.hrhelper.data.repository

import com.example.sweethome.hrhelper.data.EventsApi
import com.example.sweethome.hrhelper.data.model.App
import com.example.sweethome.hrhelper.data.model.Event
import com.example.sweethome.hrhelper.data.model.Member
import com.example.sweethome.hrhelper.extension.warning
import com.example.sweethome.hrhelper.presentation.callbacks.Carry
import com.example.sweethome.hrhelper.presentation.callbacks.DefaultCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EventRepository {
    fun loadEvents(carry: Carry<List<Event>>) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://beta-team.cft.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val eventsListApi = retrofit.create<EventsApi>(EventsApi::class.java)
        val eventsList = eventsListApi.events()
        eventsList.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    carry.onSuccess(response.body()!!)
                } else {
                    carry.onFailure()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                carry.onFailure(t)
            }
        })
    }

    fun loadMemberList(myEventId: Int, carry: Carry<List<Member>>) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://beta-team.cft.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val eventsListApi = retrofit.create<EventsApi>(EventsApi::class.java)
        val eventsList = eventsListApi.getEventMembers(myEventId)
        val defaultCallback = DefaultCallback(carry)
        eventsList.enqueue(defaultCallback)
    }

    fun updateDataBase(eventList: List<Event>) {
        Thread {
            val db = App.instance.database
            val eventDao = db?.eventDao()
            eventList.forEach { it ->
                eventDao?.insert(it)
                warning(it.title)
            }
        }.start()
    }
}