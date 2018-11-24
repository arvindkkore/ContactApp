package com.test.contactapp.persenter.view.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.persenter.adapter.CallListAdapter
import android.widget.TextView
import com.test.contactapp.R
import com.test.contactapp.data.models.CallModel


class CallFragmentNew : Fragment() {


    lateinit var callListAdapter: CallListAdapter
    lateinit var recyclerView: RecyclerView
    companion object {
        fun newInstance() = CallFragmentNew()
        val TAG = "CallFragment"

    }

    private lateinit var viewModel: CallViewModel
    val list = mutableListOf<CallModel>()

     lateinit var callLogsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.call_fragment_new, container, false)
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CallViewModel::class.java)

    }


}

