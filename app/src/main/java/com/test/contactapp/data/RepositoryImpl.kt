package com.test.contactapp.data

import android.content.Context
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.domain.repository.Repository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(val context: Context)  : Repository {

    override fun readCallLog(): Single<MutableList<CallModel>>
    {
        return com.test.contactapp.data.util.readCallLog(context)
    }

}