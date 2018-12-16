package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.domain.interfaces.MemberListRepository
import io.reactivex.Single

class GetMemberListUseCase(private val memberListRepository: MemberListRepository = App.component.createMemberRepository()) {
    fun loadEventMemberListRx(myEventId: Int): Single<List<MemberDto>> =
        memberListRepository.loadMemberListRx(myEventId)

}