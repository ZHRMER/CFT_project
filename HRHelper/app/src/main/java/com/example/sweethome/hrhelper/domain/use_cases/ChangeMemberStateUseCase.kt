package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.repository.EventRepository
import com.example.sweethome.hrhelper.di.App

class ChangeMemberStateUseCase(private val eventRepository: EventRepository = App.component.createEventRepository()) {
    fun changeMemberState(memberId: Int, eventId: Int, myIsArrived: Boolean): Int {
        return eventRepository.changeMemberArrivedState(memberId, eventId, myIsArrived)
    }
}