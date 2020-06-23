package com.rojer_ko.translator.data.datasource.room

import com.rojer_ko.translator.data.datasource.DataSourceLocal
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.utils.convertDataModelSuccessToEntity
import com.rojer_ko.translator.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImpl(private val dao: HistoryDao): DataSourceLocal<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> {
        return mapHistoryEntityToSearchResult(dao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let{
            dao.insert(it)
        }
    }
}
