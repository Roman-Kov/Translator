package com.rojer_ko.translator.presentation.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.domain.interactors.Interactor

abstract class BaseActivity<T: AppState, I : Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    abstract fun setDataToAdapter(data: List<SearchResult>)

    fun renderData(appState: AppState) {
        when (appState){
            is AppState.Success -> {
                appState.data?.let {
                    if(it.isNullOrEmpty()){
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    }
                    else{
                        setDataToAdapter(it)
                    }
                }
            }

            is AppState.Error -> {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }

            is AppState.Loading -> {
            }
        }
    }
}