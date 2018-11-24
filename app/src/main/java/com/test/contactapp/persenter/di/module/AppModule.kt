package com.test.contactapp.persenter.di.module

import android.content.Context
import com.test.contactapp.ContactApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class AppModule(val app: ContactApp)
{
  @Provides
  @Singleton
  fun provideContext ():Context = app

}