package com.example.sweethome.hrhelper.data.source.network

import com.example.sweethome.hrhelper.data.dto.EventDto
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.google.gson.JsonArray
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface EventsApi {

    @GET("Registration/members/event/{eventId}?token=cftteamtest2018")
    fun loadEventMemberListRx(@Path("eventId") eventId: Int): Single<List<MemberDto>>

    @GET("Events/registration")
    fun loadEventListRx(): Single<List<EventDto>>

    @POST("Registration/members/event/{eventId}/confirmation?token=cftteamtest2018")
    @Headers("Accept: application/json", "Content-Type: application/json")
    fun sendConfirmationMemberArrived(@Path("eventId") eventId: Int, @Body memberJSONArray: JsonArray): Observable<Example>
}