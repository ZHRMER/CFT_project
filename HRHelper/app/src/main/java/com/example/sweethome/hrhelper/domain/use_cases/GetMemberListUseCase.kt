package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.data.repository.EventRepository
import com.example.sweethome.hrhelper.di.App
import com.example.sweethome.hrhelper.presentation.callbacks.Carry

class GetMemberListUseCase(private val eventRepository: EventRepository = App.component.createEventRepository()) {
    fun getMemberList(myEventId: Int, carry: Carry<List<MemberDto>>) {
        eventRepository.loadMemberList(myEventId, carry)
    }
}