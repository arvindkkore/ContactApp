package com.test.contactapp.persenter.di.component

import com.test.contactapp.persenter.di.module.ActivityModule
import com.test.contactapp.persenter.view.activity.CallLogActivity
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class) , modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
   fun inject(callLogActivity: CallLogActivity)
}