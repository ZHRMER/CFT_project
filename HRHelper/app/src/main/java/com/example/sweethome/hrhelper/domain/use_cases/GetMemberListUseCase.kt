package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.repository.EventRepository
import com.example.sweethome.hrhelper.data.model.Member
import com.example.sweethome.hrhelper.presentation.callbacks.Carry

class GetMemberListUseCase(private val eventRepository: EventRepository = EventRepository()) {
    fun getMemberList(myEventId: Int, carry: Carry<List<Member>>) {
        eventRepository.loadMemberList(myEventId, carry)
    }
}