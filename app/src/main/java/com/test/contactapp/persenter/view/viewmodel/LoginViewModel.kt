package com.test.contactapp.persenter.view.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.test.contactapp.domain.interactor.db.SetLookupData
import javax.inject.Inject

class LoginViewModel @Inject constructor(val context: Context, val setLookupData: SetLookupData) : ViewModel(){

    companion object {
        val  TAG ="LoginViewModel"
    }
    fun setLookupData() {
        setLookupData.execute(1).subscribe ({
            Log.e(TAG,"Data inserted ${it}")
        },{e->Log.e(TAG,"Error ${e.message}")})
    }

}
