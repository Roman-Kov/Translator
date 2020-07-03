package com.rojer_ko.translator.presentation.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.domain.interactors.Interactor
import com.rojer_ko.translator.presentation.common.getMyTheme
import com.rojer_ko.utils.OnlineLiveData

abstract class BaseActivity<T: AppState, I : Interactor<T>> : AppCompatActivity() {

    abstract val layoutRes: Int
    abstract val appBar: Int?
    abstract val networkStatusImage: Int?
    abstract val model: BaseViewModel<T>
    private var networkStatusView: ImageView? = null
    protected var isNetworkAvailable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(getMyTheme(isAppBar()))
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initViews()
        checkConnection()
        setToolBar()
        subscribeToNetworkChange()
    }

    abstract fun setDataToAdapter(data: List<SearchResult>)

    private fun initViews(){
        networkStatusImage?.let {
            networkStatusView = findViewById(it)
        }
    }

    private fun checkConnection(){
        networkStatusView?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), 42)
            }
            else{
                Snackbar.make(networkStatusView!!, "Check your network connection", Snackbar.LENGTH_LONG)
                    .setAction("SETTINGS"){
                        startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0);
                    }.show()
            }
        }
    }

    private fun isAppBar():Boolean{
        return appBar != null
    }

    private fun setToolBar(){
        if (isAppBar()){
            appBar?.let {
                val toolbar: Toolbar = findViewById(it)
                setSupportActionBar(toolbar)
            }
        }
    }

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

                    networkStatusView?.visibility = View.VISIBLE
                }
                else{
                    networkStatusView?.visibility = View.GONE
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