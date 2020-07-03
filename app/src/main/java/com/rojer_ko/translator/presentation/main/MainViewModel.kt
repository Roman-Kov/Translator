package com.rojer_ko.translator.presentation.main

import androidx.lifecycle.LiveData
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }

    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(interactor.getData(word, isOnline))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.postValue(AppState.Success(null))
        super.onCleared()
    }
}