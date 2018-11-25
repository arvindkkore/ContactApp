package com.test.contactapp.persenter.di.component

import android.content.Context
import com.test.contactapp.domain.repository.Repository
import com.test.contactapp.persenter.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
public interface AppComponent {
    fun context(): Context
    fun repository(): Repository
}