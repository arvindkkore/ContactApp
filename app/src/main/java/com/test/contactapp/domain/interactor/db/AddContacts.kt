package com.test.contactapp.domain.interactor.db

import com.test.contactapp.data.RepositoryImpl
import com.test.contactapp.data.objbox.Contact
import com.test.contactapp.di.qualifier.ObserverThread
import com.test.contactapp.di.scope.ActivityScope
import com.test.contactapp.domain.interactor.BaseUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

@ActivityScope
class AddContacts @Inject constructor(val repository: RepositoryImpl, @ObserverThread val observeon: Scheduler, @ObserverThread val subscribeon: Scheduler) : BaseUseCase<MutableList<Contact>,Boolean>() {

    override fun createUsesCase(param: MutableList<Contact>?): Single<Boolean> = repository.addContacts(param!!).subscribeOn(subscribeon).observeOn(observeon)
}