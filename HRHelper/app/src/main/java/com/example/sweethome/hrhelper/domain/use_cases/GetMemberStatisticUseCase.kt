package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.repository.CommonRepository
import com.example.sweethome.hrhelper.di.App

class GetMemberStatisticUseCase(private val commonRepository: CommonRepository = App.component.createEventRepository()) {
    fun getMemberStatistic(eventId: Int): List<Int> {
        return commonRepository.getMemberStatistic(eventId)
    }
}