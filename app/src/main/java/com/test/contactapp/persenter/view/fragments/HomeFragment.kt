package com.test.contactapp.persenter.view.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.R
import com.test.contactapp.persenter.view.HomeViewModel
import com.test.contactapp.persenter.adapter.MadeCallAdapter
import com.test.contactapp.persenter.adapter.MissedCallAdapter
import com.test.contactapp.persenter.adapter.RecievedCallAdapter


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }


    lateinit var misscallAdapter: MissedCallAdapter
    lateinit var madecallAdapter: MadeCallAdapter
    lateinit var receivedcallAdapter: RecievedCallAdapter
    lateinit var recyclerViewMissedCall: RecyclerView
    lateinit var recyclerViewReceivedCall: RecyclerView
    lateinit var recyclerViewMadeCall: RecyclerView

    val listMisdedCall = mutableListOf<String>()
    val listReceivedCall = mutableListOf<String>()
    val listMadeCall = mutableListOf<String>()
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false) ;
        recyclerViewMissedCall = view.findViewById<RecyclerView>(R.id.recycler_view_home)
        recyclerViewMissedCall.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, true)
        misscallAdapter =
                MissedCallAdapter(this.activity as Context, listMisdedCall)
        recyclerViewMissedCall.adapter=misscallAdapter

        recyclerViewReceivedCall=view.findViewById<RecyclerView>(R.id.recycler_view_received)
        recyclerViewReceivedCall.layoutManager =LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, true)
        receivedcallAdapter =
                RecievedCallAdapter(this.activity as Context, listReceivedCall)
        recyclerViewReceivedCall.adapter=receivedcallAdapter

        recyclerViewMadeCall =view.findViewById<RecyclerView>(R.id.recycler_view_outgoing)
        recyclerViewMadeCall.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, true)
        madecallAdapter = MadeCallAdapter(this.activity as Context, listMadeCall)
        recyclerViewMadeCall.adapter=madecallAdapter

        return view;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

    }

}
