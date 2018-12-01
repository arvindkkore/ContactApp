package com.test.contactapp.di.module

import android.content.Context
import android.util.Log
import androidx.room.Dao
import com.test.contactapp.BuildConfig
import com.test.contactapp.data.ObjectBoxDao
import com.test.contactapp.data.objbox.MyObjectBox

import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import javax.inject.Singleton

@Module
class ObjectBoxModule {

   @Provides
   @Singleton
   fun provideBoxStore(context: Context):BoxStore{


      val boxStore = MyObjectBox.builder().androidContext(context).build()
      if (BuildConfig.DEBUG) {
         val started = AndroidObjectBrowser(boxStore).start(context)
         Log.i("ObjectBrowser", "Started: $started")
      }
      return boxStore
   }

   @Provides
   @Singleton
   fun provideDao(boxStore: BoxStore):ObjectBoxDao{
      return ObjectBoxDao(boxStore)
   }


}