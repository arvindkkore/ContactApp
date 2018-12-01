package com.test.contactapp.di.module

import android.content.Context
import android.util.Log
import com.test.contactapp.BuildConfig
import com.test.contactapp.ContactApp
import com.test.contactapp.data.ObjectBoxDao
import com.test.contactapp.data.RepositoryImpl
import com.test.contactapp.di.qualifier.ObserverThread
import com.test.contactapp.di.qualifier.SubscriberThread
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton
import io.objectbox.android.AndroidObjectBrowser
import com.test.contactapp.data.objbox.MyObjectBox




@Module
class AppModule(val app: ContactApp)
{
  @Provides
  @Singleton
  fun provideContext (): Context = app

  @Provides
  @Singleton
  fun provideRepository(context: Context, dao:ObjectBoxDao) : RepositoryImpl  {
    return RepositoryImpl(context,dao)
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