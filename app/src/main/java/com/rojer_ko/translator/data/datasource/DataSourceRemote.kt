package com.rojer_ko.translator.data.datasource

import com.rojer_ko.translator.data.datasource.retrofit.RetrofitImpl
import com.rojer_ko.translator.data.model.SearchResult
import io.reactivex.Observable

class DataSourceRemote(
    private val remoteProvider: RetrofitImpl = RetrofitImpl()): DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> {
        return remoteProvider.getData(word)
    }
}