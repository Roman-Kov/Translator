package com.rojer_ko.translator.data.repository

import com.rojer_ko.translator.data.model.AppState

interface RepositoryLocal<T>: Repository<T> {
    suspend fun saveToDB(appState: AppState)
}