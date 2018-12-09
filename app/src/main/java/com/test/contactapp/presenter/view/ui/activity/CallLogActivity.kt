package com.test.contactapp.presenter.view.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.contactapp.ContactApp
import com.test.contactapp.R
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.di.component.ActivityComponent
import com.test.contactapp.di.component.DaggerActivityComponent
import com.test.contactapp.presenter.adapter.CallLogAdapter
import com.test.contactapp.presenter.util.CommonViewModelFactory
import com.test.contactapp.presenter.view.viewmodel.CallLogViewModel
import kotlinx.android.synthetic.main.activity_call_log.*
import kotlinx.android.synthetic.main.content_call_log.*
import javax.inject.Inject


class CallLogActivity : AppCompatActivity() {
    companion object {

        val TAG = "CallLogActivity"
    }

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder().appComponent((application as ContactApp).component).build()
    }
    @Inject
    lateinit var factory: CommonViewModelFactory

    lateinit var viewModel: CallLogViewModel
    val list = mutableListOf<CallModel>()


    lateinit var adapter: CallLogAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_log)
        setSupportActionBar(toolbar)
        activityComponent.inject(this)
        viewModel = ViewModelProviders.of(this, this.factory).get(CallLogViewModel::class.java)
        recycler_view_calllog.layoutManager = LinearLayoutManager(this)
        adapter = CallLogAdapter(this, list)
        recycler_view_calllog.adapter = adapter
        shimmer_view_container.startShimmer();
        setObserver()
        viewModel.loadCallLog()
    }

    fun setObserver() {
        viewModel.getLiveData().observe(this, androidx.lifecycle.Observer<MutableList<CallModel>> { data ->
            Log.e(TAG, "size ${data.size}")
            list.clear()
            list.addAll(data)
            adapter.notifyDataSetChanged()
            shimmer_view_container.stopShimmer()
            shimmer_view_container.setVisibility(View.GONE)
        }
        )
    }

}
