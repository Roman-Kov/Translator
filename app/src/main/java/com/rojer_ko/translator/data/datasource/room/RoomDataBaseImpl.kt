package com.rojer_ko.translator.data.datasource.room

import com.rojer_ko.translator.data.datasource.DataSource
import com.rojer_ko.translator.data.model.SearchResult
import io.reactivex.Observable

class RoomDataBaseImpl: DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> {
        TODO("Not yet implemented")
    }
}