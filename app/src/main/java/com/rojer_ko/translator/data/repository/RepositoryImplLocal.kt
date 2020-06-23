package com.rojer_ko.translator.data.repository

import com.rojer_ko.translator.data.datasource.DataSourceLocal
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult

class RepositoryImplLocal(private val dataSource: DataSourceLocal<List<SearchResult>>): RepositoryLocal<List<SearchResult>> {
    override suspend fun saveToDB(appState: AppState) {
        return dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }
}