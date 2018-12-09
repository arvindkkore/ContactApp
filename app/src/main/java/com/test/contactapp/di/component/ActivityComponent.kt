package com.test.contactapp.di.component

import android.content.Context
import com.test.contactapp.ContactApp
import com.test.contactapp.data.RepositoryImpl
import com.test.contactapp.di.module.ActivityModule
import com.test.contactapp.di.qualifier.ObserverThread
import com.test.contactapp.di.qualifier.SubscriberThread
import com.test.contactapp.di.scope.ActivityScope
import com.test.contactapp.presenter.view.ui.activity.AddContactActivity
import com.test.contactapp.presenter.view.ui.activity.CallLogActivity
import com.test.contactapp.presenter.view.ui.activity.ContactDetailActivity
import com.test.contactapp.presenter.view.ui.activity.LoginActivity
import com.test.contactapp.presenter.view.viewmodel.AddEditViewContactViewModel
import dagger.Component
import io.reactivex.Scheduler

@Component(dependencies = arrayOf(AppComponent::class) , modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent {
   fun inject(callLogActivity: CallLogActivity)
   fun inject(addContactActivity: AddContactActivity)
   fun inject(loginActivity: LoginActivity)
   fun inject(contactDetailActivity: ContactDetailActivity)

   fun inject( contactApp: ContactApp)

   //passing it to downstream
   fun context(): Context
   fun repository(): RepositoryImpl

   @ObserverThread
   fun observerThread(): Scheduler

   @SubscriberThread
   fun subscriberThread(): Scheduler
   fun detailFragmentModel():AddEditViewContactViewModel
}