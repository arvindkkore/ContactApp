package com.test.contactapp.domain.interactor.provider

import com.test.contactapp.data.models.CallModel
import com.test.contactapp.domain.interactor.BaseUseCase
import com.test.contactapp.domain.repository.Repository
import com.test.contactapp.persenter.di.scope.ActivityScope
import io.reactivex.Single
import javax.inject.Inject

@ActivityScope
class ReadCallLog @Inject constructor(val repository: Repository) : BaseUseCase<Unit, MutableList<CallModel>>() {

    override fun createUsesCase(param: Unit?): Single<MutableList<CallModel>> {
      return repository.readCallLog()
    }
}