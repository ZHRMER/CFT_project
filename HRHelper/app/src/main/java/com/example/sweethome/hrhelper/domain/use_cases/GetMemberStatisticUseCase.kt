package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.repository.EventRepository
import com.example.sweethome.hrhelper.di.App

class GetMemberStatisticUseCase(private val eventRepository: EventRepository = App.component.createEventRepository()) {
    fun getMemberStatistic(eventId: Int): List<Int> {
        return eventRepository.getMemberStatistic(eventId)
    }
}