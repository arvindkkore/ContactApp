package com.test.contactapp.presenter.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.di.scope.ActivityScope
import com.test.contactapp.domain.interactor.provider.ReadCallLog
import javax.inject.Inject


//https://medium.com/@JasonCromer/making-room-less-database-queries-using-the-new-android-architecture-components-81180ba6e7e0
@ActivityScope
class CallLogViewModel @Inject constructor(val reactCallLog: ReadCallLog) : ViewModel() {
    companion object {
        val TAG = "CallLogViewModel"
    }

    object liveData : MutableLiveData<MutableList<CallModel>>()

    fun getLiveData(): LiveData<MutableList<CallModel>> =
        liveData

    fun loadCallLog() {
        reactCallLog.execute(1)
            .subscribe({ data ->
                Log.e(TAG, "Size is ${data.size}")
                liveData.value = data
            }, { error -> Log.e(TAG, "Error is ${error.message}") });


    }


}