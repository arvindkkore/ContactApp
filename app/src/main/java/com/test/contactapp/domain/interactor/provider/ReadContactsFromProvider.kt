package com.test.contactapp.domain.interactor.provider

import com.test.contactapp.data.RepositoryImpl
import com.test.contactapp.data.objbox.Contact
import com.test.contactapp.di.qualifier.ObserverThread
import com.test.contactapp.di.scope.ActivityScope
import com.test.contactapp.domain.interactor.BaseUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject


@ActivityScope
class ReadContactsFromProvider @Inject constructor(val repository: RepositoryImpl, @ObserverThread val observeon: Scheduler, @ObserverThread val subscribeon: Scheduler) : BaseUseCase<Int, MutableList<Contact>>() {

    override fun createUsesCase(param: Int?): Single<MutableList<Contact>> = repository.readContacts().subscribeOn(subscribeon).observeOn(observeon)
}