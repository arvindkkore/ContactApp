package com.test.contactapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.test.contactapp.data.db.Word


@Dao
interface WordDao {

    @Query("SELECT * from word_table")
    fun getAll(): List<Word>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: Word)

    @Query("DELETE from word_table")
    fun deleteAll()
}