package com.example.sweethome.hrhelper.data.repository

import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.data.source.database.AppDatabase
import com.example.sweethome.hrhelper.data.source.database.MemberDao
import com.example.sweethome.hrhelper.data.source.network.EventsApi
import com.example.sweethome.hrhelper.data.source.network.ServerResponse
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_DATE_FORMAT
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_MEMBER_ID
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_MEMBER_IS_VISITED
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_MEMBER_VISITED_DATE
import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.domain.interfaces.MemberListRepository
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*

class MemberListRepositoryImpl : MemberListRepository {
    private val eventsApi: EventsApi = App.component.createEventApi()
    private val roomDb: AppDatabase = App.component.createRoom()
    private val memberDao: MemberDao = roomDb.memberDao()

    override fun loadMemberListRx(myEventId: Int): Single<List<MemberDto>> =
        eventsApi.loadEventMemberListRx(myEventId)
            .doOnSuccess { saveMemberList(it, myEventId) }
            .onErrorResumeNext(getMemberListFromDb(myEventId)).flatMap { getMemberListFromDb(myEventId) }

    private fun getMemberListFromDb(myEventId: Int): Single<List<MemberDto>> =
        memberDao.getMembers(myEventId)


    private fun saveMemberList(memberList: List<MemberDto>, eventId: Int) {
        memberList.forEach {
            it.eventId = eventId
        }
        memberDao.insertAllMembers(memberList)
    }

    override fun changeMemberArrivedState(member: MemberDto): Int =
        memberDao.updateMember(member)

    override fun getMemberStatistic(eventId: Int): List<Int> {
        val statisticList = ArrayList<Int>()
        statisticList.add(memberDao.getRegisteredMemberCountEvent(eventId))
        statisticList.add(memberDao.getArrivedMemberCountEvent(eventId))
        return statisticList
    }

    override fun confirmMembersArrived(eventId: Int, memberList: List<MemberDto>): Observable<ServerResponse> {
        val memberJsonArray = JsonArray()
        val simpleDateFormat = SimpleDateFormat(KEY_DATE_FORMAT, Locale("ru"))
        memberList.forEach {
            val currentJSONMember = JsonObject()
            currentJSONMember.addProperty(KEY_MEMBER_ID, it.id)
            currentJSONMember.addProperty(KEY_MEMBER_IS_VISITED, it.isArrived)
            currentJSONMember.addProperty(KEY_MEMBER_VISITED_DATE, simpleDateFormat.format(it.visitedDate))
            memberJsonArray.add(currentJSONMember)
        }
        return eventsApi.sendConfirmationMemberArrived(eventId, memberJsonArray)
    }
}