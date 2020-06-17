package com.rojer_ko.translator.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.domain.interactors.Interactor

abstract class BaseActivity<T: AppState, I : Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    abstract fun renderData(appState: T)
}