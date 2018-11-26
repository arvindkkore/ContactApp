package com.test.contactapp.persenter.di.scope

import java.lang.annotation.RetentionPolicy
import javax.inject.Scope
//https://proandroiddev.com/dagger-2-component-relationships-custom-scopes-8d7e05e70a37
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class  ActivityScope {
}