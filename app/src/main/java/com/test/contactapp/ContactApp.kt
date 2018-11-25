package com.test.contactapp

import android.app.Application
import android.content.Context
import com.test.contactapp.persenter.di.component.AppComponent
import com.test.contactapp.persenter.di.module.AppModule

class ContactApp : Application()
{
   lateinit var component:AppComponent
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }

}
