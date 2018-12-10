package com.example.sweethome.hrhelper.data

import com.example.sweethome.hrhelper.data.model.Event
import com.example.sweethome.hrhelper.data.model.Member
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventsApi {
    @GET("Events/registration")
    fun events(): Call<List<Event>>

    @GET("Registration/members/event/{eventId}?token=cftteamtest2018")
    fun getEventMembers(
        @Path("eventId") eventId: Int
    ): Call<List<Member>>
}