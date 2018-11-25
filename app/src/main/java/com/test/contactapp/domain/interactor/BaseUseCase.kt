package com.test.contactapp.domain.interactor

import io.reactivex.Single

abstract class BaseUseCase<PARAM,RESULT>
{
    fun execute(param: PARAM): Single<RESULT> =createUsesCase(param)
    abstract fun createUsesCase(param: PARAM?): Single<RESULT>
}