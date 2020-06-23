package com.rojer_ko.translator.data.datasource

import com.rojer_ko.translator.data.model.AppState

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}