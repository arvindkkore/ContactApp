package com.test.contactapp.presenter.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.contactapp.di.scope.FragmentScope
import javax.inject.Inject

@FragmentScope
public class GlobalFViewModelFactory<T : ViewModel> @Inject constructor(val viewModel: T) : ViewModelProvider.Factory {

   companion object {
       val  TAG:String  = "GlobalFViewModelFactory"
   }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }

}
