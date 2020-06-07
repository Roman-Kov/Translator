package com.rojer_ko.translator.presentation.main

import android.util.Log
import com.rojer_ko.translator.Contract
import com.rojer_ko.translator.data.datasource.DataSourceLocal
import com.rojer_ko.translator.data.datasource.DataSourceRemote
import com.rojer_ko.translator.data.model.DataModel
import com.rojer_ko.translator.data.repository.RepositoryImpl
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainPresenterImpl<T: DataModel, V: Contract.View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val  schedulerProvider: SchedulerProvider = SchedulerProvider()
): Contract.Presenter<T, V> {
    private var currentView: V? = null

    override fun attachView(view: V) {
        if(view != currentView) currentView = view
    }

    override fun detachView(view: V) {
        if(view == currentView){
            currentView = null
            compositeDisposable.clear()
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe{
                    currentView?.renderData(DataModel.Loading(null))
                }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<DataModel>{
        return object: DisposableObserver<DataModel>(){
            override fun onComplete() {
            }

            override fun onNext(t: DataModel) {
                currentView?.renderData(t)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(DataModel.Error(e))
                Log.d("Error", e.toString())
            }
        }
    }
}