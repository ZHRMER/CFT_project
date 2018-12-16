package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.domain.interfaces.MemberListRepository

class GetMemberStatisticUseCase(private val memberListRepository: MemberListRepository = App.component.createMemberRepository()) {
    fun getMemberStatistic(eventId: Int): List<Int> =
        memberListRepository.getMemberStatistic(eventId)

}