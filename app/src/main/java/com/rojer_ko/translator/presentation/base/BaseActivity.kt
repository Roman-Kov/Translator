package com.rojer_ko.translator.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.domain.interactors.Interactor
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.main.MainViewModel

abstract class BaseActivity<T: AppState, I : Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    abstract fun renderData(appState: T)
}