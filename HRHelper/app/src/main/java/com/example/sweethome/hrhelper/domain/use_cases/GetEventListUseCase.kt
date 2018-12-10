package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.repository.EventRepository
import com.example.sweethome.hrhelper.data.model.Event
import com.example.sweethome.hrhelper.presentation.callbacks.Carry

class GetEventListUseCase(private val eventRepository: EventRepository = EventRepository()) {
    fun getEventList(carry: Carry<List<Event>>) {
        eventRepository.loadEvents(carry)
    }
}