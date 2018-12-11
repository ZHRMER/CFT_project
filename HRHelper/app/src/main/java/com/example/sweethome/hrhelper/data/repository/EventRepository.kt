package com.example.sweethome.hrhelper.data.repository

import com.example.sweethome.hrhelper.data.EventsApi
import com.example.sweethome.hrhelper.data.dto.EventDto
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.domain.entity.toEvent
import com.example.sweethome.hrhelper.extension.warning
import com.example.sweethome.hrhelper.presentation.callbacks.Carry
import com.example.sweethome.hrhelper.presentation.callbacks.DefaultCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventRepository {
    private var eventsApi: EventsApi = App.component.createEventApi()

    init {
        warning("Init repository")
    }

    fun loadEvents(carry: Carry<List<Event>>) {
        val eventsList = eventsApi.events()
        eventsList.enqueue(object : Callback<List<EventDto>> {
            override fun onResponse(call: Call<List<EventDto>>, response: Response<List<EventDto>>) {
                if (response.isSuccessful) {
                    val eventList = ArrayList<Event>()
                    response.body()?.forEach { eventList.add(it.toEvent()) }
                    carry.onSuccess(eventList)
                } else {
                    carry.onFailure()
                }
            }

            override fun onFailure(call: Call<List<EventDto>>, t: Throwable) {
                carry.onFailure(t)
            }
        })
    }

    fun loadMemberList(myEventId: Int, carry: Carry<List<MemberDto>>) {
        val eventsList = eventsApi.getEventMembers(myEventId)
        val defaultCallback = DefaultCallback(carry)
        eventsList.enqueue(defaultCallback)
    }

}