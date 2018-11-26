package com.test.contactapp.persenter.di.component

import com.test.contactapp.persenter.di.module.ActivityModule
import com.test.contactapp.persenter.di.scope.ActivityScope
import com.test.contactapp.persenter.view.ui.activity.CallLogActivity
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class) , modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent {
   fun inject(callLogActivity: CallLogActivity)
}