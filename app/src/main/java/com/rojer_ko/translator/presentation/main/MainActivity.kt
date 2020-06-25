package com.rojer_ko.translator.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.domain.interactors.MainInteractor
import com.rojer_ko.translator.presentation.base.BaseActivity
import com.rojer_ko.translator.presentation.description.DescriptionActivity
import com.rojer_ko.translator.presentation.history.HistoryActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<AppState, MainInteractor>() {

    override val model: MainViewModel by viewModel()

    private var adapter: MainAdapter? = null
    private val onItemClickListener: MainAdapter.OnListItemClickLestener =

        object : MainAdapter.OnListItemClickLestener{
            override fun onItemClick(data: SearchResult){
                startActivity(DescriptionActivity.getIntent(
                    applicationContext,
                    data.text!!,
                    data.meanings!![0].translation!!.translation!!,
                    data.meanings[0].imageUrl
                ))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        searchBtnClick()
    }

    private fun initViewModel(){
        check(mainRecyclerView.adapter == null){"The ViewModel should be initialised first" }
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun searchBtnClick(){
        searchBtn.setOnClickListener {
            model.getData(searchText.text.toString(), true)
        }
    }

    override fun setDataToAdapter(data: List<SearchResult>) {
        if (adapter == null) {
            adapter = MainAdapter(onItemClickListener, data)
            mainRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            mainRecyclerView.adapter = adapter
        } else {
            adapter!!.setData(data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
