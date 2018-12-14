package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.repository.CommonRepository
import com.example.sweethome.hrhelper.domain.entity.Event
import com.example.sweethome.hrhelper.domain.entity.toEvent
import io.reactivex.Single

class GetEventListUseCase(private val commonRepository: CommonRepository = CommonRepository()) {

    fun loadEventRx(): Single<List<Event>> =
        commonRepository.loadEventListRx()
            .map { list ->
                list.map {
                    it.toEvent()
                }
            }

}