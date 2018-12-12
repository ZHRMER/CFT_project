package com.example.sweethome.hrhelper.data.network_source

import com.example.sweethome.hrhelper.data.dto.EventDto
import com.example.sweethome.hrhelper.data.dto.MemberDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface EventsApi {

    @GET("Registration/members/event/{eventId}?token=cftteamtest2018")
    fun loadEventMemberListRx(@Path("eventId") eventId: Int): Single<List<MemberDto>>

    @GET("Events/registration")
    fun loadEventListRx(): Single<List<EventDto>>
}