package com.test.contactapp.domain.interactor

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseUseCase<PARAM,RESULT>
{
    fun execute(param: PARAM): Single<RESULT> {
        return createUsesCase(param)

    }
        abstract fun createUsesCase(param: PARAM?): Single<RESULT>

}