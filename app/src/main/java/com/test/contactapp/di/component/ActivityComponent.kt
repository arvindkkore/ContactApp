package com.test.contactapp.di.component

import com.test.contactapp.di.module.ActivityModule
import com.test.contactapp.di.scope.ActivityScope
import com.test.contactapp.persenter.view.ui.activity.AddContactActivity
import com.test.contactapp.persenter.view.ui.activity.CallLogActivity
import com.test.contactapp.persenter.view.ui.activity.LoginActivity
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class) , modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent {
   fun inject(callLogActivity: CallLogActivity)
   fun inject(addContactActivity: AddContactActivity)
   fun inject(loginActivity: LoginActivity)
}