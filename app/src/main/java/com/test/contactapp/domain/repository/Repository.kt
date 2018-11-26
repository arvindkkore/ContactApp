package com.test.contactapp.domain.repository

import com.test.contactapp.data.models.CallModel
import io.reactivex.Single
import javax.inject.Singleton


public interface Repository
{
  public  fun readCallLog(): Single<MutableList<CallModel>>
}
