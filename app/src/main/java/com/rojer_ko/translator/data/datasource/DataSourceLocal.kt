package com.rojer_ko.translator.data.datasource.room

import com.rojer_ko.translator.data.datasource.DataSource
import com.rojer_ko.translator.data.model.AppState

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}