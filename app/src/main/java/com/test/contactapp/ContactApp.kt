package com.test.contactapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.test.contactapp.di.component.AppComponent
import com.test.contactapp.di.component.DaggerAppComponent
import com.test.contactapp.di.module.AppModule

class ContactApp : Application()
{
   val component:AppComponent by lazy {
       DaggerAppComponent.builder()
           .appModule(AppModule(this)).build()
   }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)

    }
    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        MultiDex.install(context)
    }


}
