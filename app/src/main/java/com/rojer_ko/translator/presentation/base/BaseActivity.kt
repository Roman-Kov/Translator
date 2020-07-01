package com.rojer_ko.translator.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.domain.interactors.Interactor
import com.rojer_ko.utils.OnlineLiveData

abstract class BaseActivity<T: AppState, I : Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToNetworkChange()
    }

    abstract fun setDataToAdapter(data: List<SearchResult>)

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(
            this@BaseActivity,
            Observer<Boolean> {
                isNetworkAvailable = it
                if (!isNetworkAvailable) {
                    Toast.makeText(
                        this@BaseActivity,
                        R.string.dialog_message_device_is_offline,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }


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