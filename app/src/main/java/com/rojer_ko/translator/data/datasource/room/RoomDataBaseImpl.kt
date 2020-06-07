package com.rojer_ko.translator.data.datasource.room

import com.rojer_ko.translator.Contract
import com.rojer_ko.translator.data.model.SearchResult
import io.reactivex.Observable

class RoomDataBaseImpl: Contract.DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> {
        TODO("Not yet implemented")
    }
}