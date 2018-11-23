package com.test.contactapp

import android.content.Context
import android.database.Cursor
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.adapter.CallListAdapter
import kotlinx.android.synthetic.main.call_fragment.*
import android.provider.CallLog
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import android.text.Html
import android.util.Log
import android.widget.TextView
import com.test.contactapp.models.CallModel
import java.lang.Long
import java.util.*


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

