package com.test.contactapp.persenter.di.module

import android.content.Context
import com.test.contactapp.ContactApp
import com.test.contactapp.data.RepositoryImpl
import com.test.contactapp.persenter.di.qualifier.ObserverThread
import com.test.contactapp.persenter.di.qualifier.SubscriberThread
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton


@Module
class AppModule(val app: ContactApp)
{
  @Provides
  @Singleton
  fun provideContext (): Context = app

  @Provides
  @Singleton
  fun provideRepository(context: Context) : RepositoryImpl  {
    return RepositoryImpl(context)
  }

  @Provides
  @Singleton
  @SubscriberThread
  fun provideSubscriberThread():Scheduler =Schedulers.io()

  @Provides
  @Singleton
  @ObserverThread
  fun provideObserverThread():Scheduler = AndroidSchedulers.mainThread()

}