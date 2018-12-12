package com.example.sweethome.hrhelper.presentation.screens.settings.presenter

import android.support.v7.app.AppCompatActivity
import com.example.sweethome.hrhelper.domain.use_cases.GetMemberStatisticUseCase
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SettingsPresenter(
    private var myActivity: AppCompatActivity?,
    private var mySettingsPresenterContract: SettingsPresenterContract?,
    private var getMemberStatisticUseCase: GetMemberStatisticUseCase = GetMemberStatisticUseCase(),
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
) {

    fun attach(
        activity: AppCompatActivity?,
        settingsPresenterContract: SettingsPresenterContract
    ) {
        myActivity = activity
        mySettingsPresenterContract = settingsPresenterContract
    }

    fun detach() {
        myActivity = null
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
                    mySettingsPresenterContract?.updateViewStatistics(it[0], it[1])
                }, {
                })
        )
    }

    interface SettingsPresenterContract {
        fun updateViewStatistics(allMember: Int, registerMember: Int)
    }
}