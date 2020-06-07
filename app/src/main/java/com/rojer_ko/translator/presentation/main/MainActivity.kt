package com.rojer_ko.translator.presentation.main

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.translator.Contract
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.DataModel
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity<DataModel>() {

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

        searchBtn.setOnClickListener {
            presenter.getData(searchText.text.toString(), true)
        }
    }

    override fun createPresenter(): Contract.Presenter<DataModel, Contract.View> {
        return MainPresenterImpl()
    }

    override fun renderData(dataModel: DataModel) {
        when (dataModel){
            is DataModel.Success -> {
                if(dataModel.data == null || dataModel.data.isEmpty()){
                    Log.d("Error", "null or empty")
                }
                else{
                    if(adapter == null){
                        adapter =  MainAdapter(onItemClickListener, dataModel.data)
                        historyRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        historyRecyclerView.adapter = adapter
                        Toast.makeText(this@MainActivity, "Null", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        adapter!!.setData(dataModel.data)
                        Toast.makeText(this@MainActivity, "NotNull", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            is DataModel.Error -> {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }

            is DataModel.Loading -> {

            }
        }

    }
}
