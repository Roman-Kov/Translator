package com.rojer_ko.translator.data.repository

import com.rojer_ko.translator.data.datasource.DataSource
import com.rojer_ko.translator.data.model.SearchResult

class RepositoryImpl (private val dataSource: DataSource<List<SearchResult>>):
        Repository<List<SearchResult>>{

    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }
}