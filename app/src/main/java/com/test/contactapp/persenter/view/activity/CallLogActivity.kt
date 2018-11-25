package com.test.contactapp.persenter.view.activity

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.contactapp.R
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.persenter.view.viewmodel.CallLogViewModel

import kotlinx.android.synthetic.main.activity_call_log.*
import kotlinx.android.synthetic.main.content_call_log.*
import androidx.lifecycle.ViewModelProviders
import com.test.contactapp.persenter.adapter.CallLogAdapter
import com.test.contactapp.persenter.di.component.ActivityComponent
import com.test.contactapp.persenter.util.CommonViewModelFactory
import javax.inject.Inject


class CallLogActivity : AppCompatActivity(){


    lateinit var activityComponent: ActivityComponent
    @Inject
    lateinit var factory: CommonViewModelFactory

    lateinit var viewModel : CallLogViewModel
    val  list = mutableListOf<CallModel>()

    companion object {

        val TAG ="CallLogActivity"
    }

    lateinit var adapter : CallLogAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_log)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, this.factory).get(CallLogViewModel::class.java)

        recycler_view_calllog.layoutManager =LinearLayoutManager(this)
        adapter = CallLogAdapter(this, list)
        recycler_view_calllog.adapter =adapter
        setObserver()
        viewModel.loadCallLog()
    }

    fun  setObserver()
    {
       viewModel.getLiveData().observe(this,androidx.lifecycle.Observer<MutableList<CallModel>>{data ->
          Log.e(TAG,"size ${data.size}")
           list.clear()
          list.addAll(data)
          adapter.notifyDataSetChanged()
       }
       )
    }

}
