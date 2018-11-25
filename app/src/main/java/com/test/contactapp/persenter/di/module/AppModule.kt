package com.test.contactapp.persenter.di.module

import android.content.Context
import com.test.contactapp.ContactApp
import com.test.contactapp.data.RepositoryImpl
import com.test.contactapp.domain.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: ContactApp)
{
  @Provides
  @Singleton
  fun provideContext (): Context = app

  @Provides
  @Singleton
  fun provideRepository(context: Context): Repository  =   RepositoryImpl(context)

}