package com.test.contactapp.persenter.di.module

import android.content.Context
import com.test.contactapp.data.objbox.MyObjectBox

import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class ObjectBoxModule {

   @Provides
   @Singleton
   fun provideBoxStore(context: Context):BoxStore{
      return MyObjectBox.builder()
         .androidContext(context).build()
   }



}