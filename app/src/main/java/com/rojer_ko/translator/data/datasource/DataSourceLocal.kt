package com.rojer_ko.translator.data.datasource

import com.rojer_ko.translator.data.datasource.room.RoomDataBaseImpl
import com.rojer_ko.translator.data.model.SearchResult
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImpl = RoomDataBaseImpl()) :
    DataSource<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> = remoteProvider.getData(word)
}