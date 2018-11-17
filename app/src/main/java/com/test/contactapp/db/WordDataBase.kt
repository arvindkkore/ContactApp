package com.test.contactapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.contactapp.db.dao.WordDao

@Database(entities = arrayOf(Word::class), version = 1)
abstract class WordDataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: WordDataBase? = null
        fun getInstance(context: Context): WordDataBase? {
            if (INSTANCE == null) {
                synchronized(WordDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        WordDataBase::class.java, "my_contact.db")
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}