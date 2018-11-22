package com.test.contactapp.di.component

import android.content.Context
import com.test.contactapp.di.module.AppModule
import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun context() :Context

}