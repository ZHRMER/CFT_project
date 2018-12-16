package com.example.sweethome.hrhelper.domain.interfaces

import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.data.source.network.ServerResponse
import io.reactivex.Observable
import io.reactivex.Single

interface MemberListRepository {
    fun loadMemberListRx(myEventId: Int): Single<List<MemberDto>>
    fun changeMemberArrivedState(member: MemberDto): Int
    fun getMemberStatistic(eventId: Int): List<Int>
    fun confirmMembersArrived(eventId: Int, memberList: List<MemberDto>): Observable<ServerResponse>
}