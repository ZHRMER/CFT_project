package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.data.source.network.ServerResponse
import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.domain.interfaces.MemberListRepository
import io.reactivex.Observable

class ConfirmArrivedMembersUseCase(private val memberListRepository: MemberListRepository = App.component.createMemberRepository()) {
    fun confirmArrivedMembers(memberList: List<MemberDto>, eventId: Int): Observable<ServerResponse> =
        memberListRepository.confirmMembersArrived(eventId, memberList)

}