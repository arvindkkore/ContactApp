package com.test.contactapp.presenter.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.contactapp.di.scope.ActivityScope
import com.test.contactapp.presenter.view.viewmodel.CallLogViewModel
import javax.inject.Inject

@Deprecated("using GlobalViewModelFactory instead")
@ActivityScope
class CommonViewModelFactory @Inject constructor(val viewModel: CallLogViewModel) : ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(CallLogViewModel::class.java)) {
               return  viewModel as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }

}