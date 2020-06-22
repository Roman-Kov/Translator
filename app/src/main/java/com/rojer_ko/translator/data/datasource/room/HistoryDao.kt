package com.rojer_ko.translator.data.datasource.room

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    suspend fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entitis: List<HistoryEntity>)

    @Update
    suspend fun update(entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: HistoryEntity)
}