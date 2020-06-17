package com.rojer_ko.translator.presentation.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<AppState, MainInteractor>() {

    override val model: MainViewModel by viewModel()

    private var adapter: MainAdapter? = null
    private val onItemClickListener: MainAdapter.OnListItemClickLestener =

        object : MainAdapter.OnListItemClickLestener{
            override fun onItemClick(data: SearchResult){
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        searchBtnClick()
    }

    private fun initViewModel(){
        check(historyRecyclerView.adapter == null){"The ViewModel should be initialised first" }
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun searchBtnClick(){
        searchBtn.setOnClickListener {
            model.getData(searchText.text.toString(), true)
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
