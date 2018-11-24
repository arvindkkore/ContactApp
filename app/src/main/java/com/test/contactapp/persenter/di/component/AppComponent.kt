package com.test.contactapp.persenter.di.component

import android.content.Context
import com.test.contactapp.persenter.di.module.AppModule
import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun context() :Context

}