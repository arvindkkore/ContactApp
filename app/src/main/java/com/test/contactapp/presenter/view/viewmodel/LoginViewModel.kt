package com.test.contactapp.presenter.view.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.test.contactapp.domain.interactor.db.AddContacts
import com.test.contactapp.domain.interactor.db.SetLookupData
import com.test.contactapp.domain.interactor.provider.ReadContactsFromProvider
import javax.inject.Inject

class LoginViewModel @Inject constructor(val context: Context, val setLookupData: SetLookupData,val addContacts: AddContacts,val readContactsFromProvider: ReadContactsFromProvider) : ViewModel(){

    companion object {
        val  TAG ="LoginViewModel"
    }
    fun setLookupData() {
        setLookupData.execute(1).subscribe ({
            Log.e(TAG,"Data inserted ${it}")
        },{e->Log.e(TAG,"Error ${e.message}")})
    }

    fun saveContacts() {
        readContactsFromProvider.execute(1).flatMap { addContacts.execute(it) }.subscribe (
            {result ->Log.e(TAG," Result is${result}") }, { error -> Log.e(TAG," error is${error}")})

        }

}
