package com.rojer_ko.historyscreen

import android.os.Bundle
import android.view.MenuItem
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.domain.interactors.HistoryInteractor
import com.rojer_ko.translator.presentation.base.BaseActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.scope.currentScope

class HistoryActivity :  BaseActivity<AppState, HistoryInteractor>() {

    init {
        injectDependencies()
    }

    override val layoutRes: Int =  R.layout.activity_history
    override val appBar: Int? = null
    override val networkStatusImage: Int? = null
    override val model: HistoryViewModel by currentScope.inject()
    private var adapter: HistoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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