package com.rojer_ko.translator.presentation.history

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.domain.interactors.HistoryInteractor
import com.rojer_ko.translator.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer

class HistoryActivity :  BaseActivity<AppState, HistoryInteractor>() {

    override val model: HistoryViewModel by viewModel()
    private var adapter: HistoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setActionbarHomeButtonAsUp()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setDataToAdapter(data: List<SearchResult>) {
        if (adapter == null) {
            adapter = HistoryAdapter(data)
            historyRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            historyRecyclerView.adapter = adapter
        } else {
            adapter!!.setData(data)
        }
    }

    private fun initViewModel(){
        check(historyRecyclerView.adapter == null){"The ViewModel should be initialised first" }
        model.subscribe().observe(this@HistoryActivity, Observer<AppState> { renderData(it) })
    }

    private fun setActionbarHomeButtonAsUp(){
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}