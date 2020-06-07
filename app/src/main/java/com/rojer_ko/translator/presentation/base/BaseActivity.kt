package com.rojer_ko.translator.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rojer_ko.translator.Contract
import com.rojer_ko.translator.data.model.DataModel

abstract class BaseActivity<T: DataModel> : AppCompatActivity(), Contract.View {

    protected lateinit var presenter: Contract.Presenter<T, Contract.View>

    protected abstract fun createPresenter(): Contract.Presenter<T, Contract.View>

    abstract override fun renderData(dataModel: DataModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop(){
        super.onStop()
        presenter.detachView(this)
    }

}