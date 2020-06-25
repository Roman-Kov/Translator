package com.rojer_ko.utils

import com.rojer_ko.translator.data.datasource.room.HistoryEntity
import com.rojer_ko.translator.data.model.AppState
import com.rojer_ko.translator.data.model.Meanings
import com.rojer_ko.translator.data.model.SearchResult
import com.rojer_ko.translator.data.model.Translation

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>):List<SearchResult>{
    val parsedList = arrayListOf<SearchResult>()
    if (!list.isNullOrEmpty()) {
        for (item in list) parsedList.add(
            SearchResult(
                item.word,
                listOf(Meanings(Translation(item.description), null))
            )
        )
    }
    return parsedList
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity {

    return if(appState is AppState.Success && !appState.data.isNullOrEmpty()){
        HistoryEntity(appState.data[0].text!!, appState.data[0].meanings!![0].translation?.translation)
    }
    else HistoryEntity("", "")
}