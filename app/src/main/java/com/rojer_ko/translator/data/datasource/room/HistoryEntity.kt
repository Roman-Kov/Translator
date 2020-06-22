package com.rojer_ko.translator.data.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["word"], unique = true)])
class HistoryEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "word")
    var word: String,

    @field:ColumnInfo(name = "description")
    var description: String?)