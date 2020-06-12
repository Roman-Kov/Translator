package com.rojer_ko.translator.data.datasource

import com.rojer_ko.translator.data.datasource.retrofit.RetrofitImp
import com.rojer_ko.translator.data.model.SearchResult
import io.reactivex.Observable

class DataSourceRemote(
    private val remoteProvider: RetrofitImp = RetrofitImp()): DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> {
        return remoteProvider.getData(word)
    }
}