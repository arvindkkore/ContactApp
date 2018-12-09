package com.test.contactapp.presenter.view.ui.fragments

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.R
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.presenter.adapter.MadeCallAdapter
import com.test.contactapp.presenter.adapter.MissedCallAdapter
import com.test.contactapp.presenter.adapter.RecievedCallAdapter
import com.test.contactapp.presenter.view.viewmodel.HomeViewModel
import java.lang.Long
import java.util.*


class HomeFragment : Fragment(),LoaderManager.LoaderCallbacks<Cursor> {


    companion object {
        fun newInstance() = HomeFragment()
    }

    val list = mutableListOf<CallModel>()
    lateinit var misscallAdapter: MissedCallAdapter
    lateinit var madecallAdapter: MadeCallAdapter
    lateinit var receivedcallAdapter: RecievedCallAdapter
    lateinit var recyclerViewMissedCall: RecyclerView
    lateinit var recyclerViewReceivedCall: RecyclerView
    lateinit var recyclerViewMadeCall: RecyclerView

    val listMissedCall = mutableListOf<CallModel>()
    val listReceivedCall = mutableListOf<CallModel>()
    val listMadeCall = mutableListOf<CallModel>()
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false) ;
        recyclerViewMissedCall = view.findViewById<RecyclerView>(R.id.recycler_view_home)
        recyclerViewMissedCall.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, true)
        misscallAdapter = MissedCallAdapter(this.activity as Context, listMissedCall)
        recyclerViewMissedCall.adapter=misscallAdapter

        recyclerViewReceivedCall=view.findViewById<RecyclerView>(R.id.recycler_view_received)
        recyclerViewReceivedCall.layoutManager =LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, true)
        receivedcallAdapter =  RecievedCallAdapter(this.activity as Context, listReceivedCall)
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
        loaderManager.initLoader(CallFragment.URL_LOADER, null, this@HomeFragment);


    }

    override   fun onCreateLoader(loaderID: Int, args: Bundle?): Loader<Cursor> {
        list.clear()
        Log.d(CallFragment.TAG, "onCreateLoader() >> loaderID : $loaderID")



        // Returns a new CursorLoader
        return CursorLoader(
            this.activity as Context, // Parent activity context
            CallLog.Calls.CONTENT_URI, null, null, null, null// Default sort order
        )// Table to query
        // Projection to return
        // No selection clause
        // No selection arguments


    }
    override fun onLoadFinished(loader: Loader<Cursor>, managedCursor: Cursor) {
        Log.d(CallFragment.TAG, "onLoadFinished()")


        val name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
        val number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
        val type = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
        val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)
        val duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION)

        /*  sb.append("<h4>Call Log Details <h4>")
          sb.append("\n")
          sb.append("\n")

          sb.append("<table>")*/

        while (managedCursor.moveToNext()) {
            val callModel: CallModel = CallModel()
            val phName = managedCursor.getString(name)
            callModel.contactName=phName
            val phNumber = managedCursor.getString(number)
            callModel.contactNumber=phNumber
            val callType = managedCursor.getString(type)
            val callDate = managedCursor.getString(date)
            callModel.callDate=callDate
            val callDayTime = Date(Long.valueOf(callDate))
            callModel.callDayTime=callDayTime
            val callDuration = managedCursor.getString(duration)
            callModel.duration=callDuration
            var dir: String? = null

            val callTypeCode = Integer.parseInt(callType)
            when (callTypeCode) {
                CallLog.Calls.OUTGOING_TYPE ->  callModel.type = "Outgoing"

                CallLog.Calls.INCOMING_TYPE -> callModel.type = "Incoming"

                CallLog.Calls.MISSED_TYPE -> callModel.type = "Missed"
            }


            list.add(callModel)
            Log.e("HomeFragment","Call details ${callModel}")
        }

        managedCursor.close()

        //callLogsTextView.setText(Html.fromHtml(sb.toString()))
       categorizeLog(list)
    }

    private fun categorizeLog(list: MutableList<CallModel>) {
        val missedcall =list.filter { callModel -> callModel.type == "Missed" }
        val incommingcall =list.filter { callModel -> callModel.type == "Incoming" }
        val outgoingCall =list.filter { callModel -> callModel.type == "Outgoing" }

        listMissedCall.clear();
        listMissedCall.addAll(missedcall)
        misscallAdapter.notifyDataSetChanged()

        listReceivedCall.clear();
        listReceivedCall.addAll(incommingcall)
        receivedcallAdapter.notifyDataSetChanged()

        listMadeCall.clear();
        listMadeCall.addAll(outgoingCall)
        madecallAdapter.notifyDataSetChanged()

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
