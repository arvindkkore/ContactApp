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
class SetLookupData @Inject constructor(val repository: RepositoryImpl, @ObserverThread val observeon: Scheduler, @ObserverThread val subscribeon: Scheduler) : BaseUseCase<Int, Boolean>() {

    override fun createUsesCase(param: Int?): Single<Boolean> {
        return repository.prepareLookData().subscribeOn(subscribeon).observeOn(observeon)
    }

}