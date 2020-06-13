package com.rojer_ko.translator.presentation.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.di.ViewModelFactory
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity: BaseActivity<AppState, MainInteractor>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    override lateinit var model: MainViewModel

//    override val model: MainViewModel by lazy{
//        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
//    }
    private val observer = Observer<AppState>{renderData(it)}
    private var adapter: MainAdapter? = null
    private val onItemClickListener: MainAdapter.OnListItemClickLestener =
        object : MainAdapter.OnListItemClickLestener{
            override fun onItemClick(data: SearchResult){
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = viewModelFactory.create(MainViewModel::class.java)
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })

        searchBtn.setOnClickListener {
            model.getData(searchText.text.toString(), true).observe(this@MainActivity, observer)
        }
    }

    override fun renderData(appState: AppState) {
        when (appState){
            is AppState.Success -> {
                if(appState.data == null || appState.data.isEmpty()){
                    Log.d("Error", "null or empty")
                }
                else{
                    if(adapter == null){
                        adapter =  MainAdapter(onItemClickListener, appState.data)
                        historyRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        historyRecyclerView.adapter = adapter
                    }
                    else{
                        adapter!!.setData(appState.data)
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
