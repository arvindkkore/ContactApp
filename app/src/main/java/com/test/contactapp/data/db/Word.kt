package com.test.contactapp.data.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey



@Entity(tableName = "word_table")
data class Word (@PrimaryKey @NonNull
                 @ColumnInfo(name = "word") var mWord: String )
