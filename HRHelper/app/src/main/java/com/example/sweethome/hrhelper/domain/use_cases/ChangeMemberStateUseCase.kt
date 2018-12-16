package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.domain.interfaces.MemberListRepository

class ChangeMemberStateUseCase(private val memberListRepository: MemberListRepository = App.component.createMemberRepository()) {
    fun changeMemberState(member: MemberDto): Int =
        memberListRepository.changeMemberArrivedState(member)

}