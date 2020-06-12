package com.rojer_ko.translator.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.main.MainViewModel

abstract class BaseActivity<T: AppState, V: MainInteractor> : AppCompatActivity() {

    abstract val model: MainViewModel
    abstract fun renderData(appState: AppState)
}