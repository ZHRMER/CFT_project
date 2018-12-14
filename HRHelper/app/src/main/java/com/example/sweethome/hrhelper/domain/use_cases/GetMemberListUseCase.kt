package com.example.sweethome.hrhelper.domain.use_cases

import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.data.repository.CommonRepository
import com.example.sweethome.hrhelper.di.App
import io.reactivex.Single

class GetMemberListUseCase(private val commonRepository: CommonRepository = App.component.createEventRepository()) {
    fun loadEventMemberListRx(myEventId: Int): Single<List<MemberDto>> {
        return commonRepository.loadMemberListRx(myEventId)
    }
}