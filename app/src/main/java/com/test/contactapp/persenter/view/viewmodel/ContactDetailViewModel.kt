package com.test.contactapp.persenter.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.di.scope.ActivityScope
import com.test.contactapp.domain.interactor.db.AddContact
import com.test.contactapp.domain.interactor.db.GetContactFromDB
import javax.inject.Inject

//https://medium.com/@JasonCromer/making-room-less-database-queries-using-the-new-android-architecture-components-81180ba6e7e0
@ActivityScope
class ContactDetailViewModel @Inject constructor(val addContact: AddContact,val getContact: GetContactFromDB) : ViewModel() {
    companion object {
        val TAG = "CallLogViewModel"
    }
    val contactId:Long? =0

    object liveData : MutableLiveData<MutableList<CallModel>>()

    fun getLiveData(): LiveData<MutableList<CallModel>> =
        liveData

    /*fun addContact() {
        addContact.execute(1)
            .subscribe({ data ->
                Log.e(TAG, "Size is ${data.size}")
                liveData.value = data
            }, { error -> Log.e(TAG, "Error is ${error.message}") });
    }

    fun getContact() {
        addContact.execute(1)
            .subscribe({ data ->
                Log.e(TAG, "Size is ${data.size}")
                liveData.value = data
            }, { error -> Log.e(TAG, "Error is ${error.message}") });
    }*/


}