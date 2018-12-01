package com.test.contactapp.domain.interactor.db

import com.test.contactapp.data.RepositoryImpl
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.data.objbox.Contact
import com.test.contactapp.domain.interactor.BaseUseCase
import com.test.contactapp.di.qualifier.ObserverThread
import com.test.contactapp.di.scope.ActivityScope
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

@ActivityScope
class GetContactFromDB @Inject constructor(val repository: RepositoryImpl, @ObserverThread val observeon: Scheduler, @ObserverThread val subscribeon: Scheduler) : BaseUseCase<Long, Contact>() {

    override fun createUsesCase(param: Long?): Single<Contact> = repository.getCotactDB(param).subscribeOn(subscribeon).observeOn(observeon)
}