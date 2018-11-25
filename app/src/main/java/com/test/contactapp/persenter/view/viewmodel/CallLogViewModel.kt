package com.test.contactapp.persenter.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.test.contactapp.data.models.CallModel
import com.test.contactapp.domain.interactor.provider.ReadCallLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


//https://medium.com/@JasonCromer/making-room-less-database-queries-using-the-new-android-architecture-components-81180ba6e7e0
class CallLogViewModel @Inject constructor(val reactCallLog: ReadCallLog) : ViewModel()
{
  companion object {
      val TAG = "CallLogViewModel"
  }
  object liveData: MutableLiveData<MutableList<CallModel>>()

   fun getLiveData():LiveData<MutableList<CallModel>> =
       liveData

  fun loadCallLog()
 {
     reactCallLog.execute(null!!).
     subscribeOn(Schedulers.io())
       .observeOn(AndroidSchedulers.mainThread())
         .subscribe({ data ->
             Log.e(TAG,"Size is ${data.size}")
             liveData.value =data
         },{ error->  Log.e(TAG,"Error is ${error.message}")});


 }


}