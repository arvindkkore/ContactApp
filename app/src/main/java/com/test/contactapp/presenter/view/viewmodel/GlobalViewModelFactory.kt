package com.test.contactapp.presenter.view.viewmodel

import android.util.Log;
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.contactapp.di.scope.ActivityScope

import javax.inject.Inject;

@ActivityScope
public class GlobalViewModelFactory<T : ViewModel> @Inject constructor(val viewModel: T) : ViewModelProvider.Factory {

   companion object {
       val  TAG:String  = "GlobalViewModelFactory"
   }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.e(TAG,"created")
        return viewModel as T
    }

}
