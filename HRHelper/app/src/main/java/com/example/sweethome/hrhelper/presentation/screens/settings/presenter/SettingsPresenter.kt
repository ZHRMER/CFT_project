package com.example.sweethome.hrhelper.presentation.screens.settings.presenter

import com.example.sweethome.hrhelper.data.utils.Constants.KEY_ARRIVED_MEMBER
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_REGISTERED_MEMBER
import com.example.sweethome.hrhelper.domain.use_cases.GetMemberStatisticUseCase
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SettingsPresenter(
    private var mySettingsPresenterContract: SettingsPresenterContract?
) {
    private var getMemberStatisticUseCase: GetMemberStatisticUseCase = GetMemberStatisticUseCase()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun attach(settingsPresenterContract: SettingsPresenterContract) {
        mySettingsPresenterContract = settingsPresenterContract
    }

    fun detach() {
        mySettingsPresenterContract = null
        compositeDisposable.clear()
    }

    fun updateStatistics(myEventId: Int) {
        compositeDisposable.add(
            Single.create(SingleOnSubscribe<List<Int>> { emitter ->
                emitter.onSuccess(getMemberStatisticUseCase.getMemberStatistic(myEventId))
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.size > 1) {
                        mySettingsPresenterContract?.updateViewStatistics(
                            it[KEY_REGISTERED_MEMBER],
                            it[KEY_ARRIVED_MEMBER]
                        )
                    }
                }, {
                })
        )
    }

    interface SettingsPresenterContract {
        fun updateViewStatistics(allMember: Int, registerMember: Int)
    }
}