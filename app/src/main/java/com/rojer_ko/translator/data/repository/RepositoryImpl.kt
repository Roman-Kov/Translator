package com.rojer_ko.translator.data.repository

import com.rojer_ko.translator.data.datasource.DataSource
import com.rojer_ko.translator.data.model.SearchResult
import io.reactivex.Observable

class RepositoryImpl (private val dataSource: DataSource<List<SearchResult>>):
        Repository<List<SearchResult>>{
    override fun getData(word: String): Observable<List<SearchResult>> {
        return dataSource.getData(word)
    }
}