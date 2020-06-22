package com.rojer_ko.translator.data.datasource.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class HistoryDataBase: RoomDatabase(){

    abstract fun historyDao(): HistoryDao
}