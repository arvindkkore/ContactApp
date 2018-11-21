package com.test.contactapp.di.module

import android.content.Context
import com.test.contactapp.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class AppModule(val app: MyApp)
{

  @Provides
  fun provideContext ():Context = app

}