package com.rojer_ko.translator.presentation.main

import androidx.lifecycle.LiveData
import com.rojer_ko.translator.data.datasource.DataSourceLocal
import com.rojer_ko.translator.data.datasource.DataSourceRemote
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.repository.RepositoryImpl
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.base.BaseViewModel
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {
    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe{liveDataForViewToObserve.value = AppState.Loading(null)}
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState>{
        return object: DisposableObserver<AppState>(){
            override fun onNext(t: AppState) {
                appState = t
                liveDataForViewToObserve.value = t
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }
            override fun onComplete() {
            }
        }
    }
}