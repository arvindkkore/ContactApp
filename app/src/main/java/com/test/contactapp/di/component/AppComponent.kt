package com.test.contactapp.di.component

import android.content.Context
import com.test.contactapp.ContactApp
import com.test.contactapp.data.RepositoryImpl
import com.test.contactapp.di.module.AppModule
import com.test.contactapp.di.module.ObjectBoxModule
import com.test.contactapp.di.qualifier.ObserverThread
import com.test.contactapp.di.qualifier.SubscriberThread
import dagger.Component
import io.reactivex.Scheduler
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,ObjectBoxModule::class))
public interface AppComponent {

    fun inject( contactApp: ContactApp)
    fun context(): Context
    fun repository(): RepositoryImpl

    @ObserverThread
    fun observerThread():Scheduler

    @SubscriberThread
    fun subscriberThread():Scheduler
}