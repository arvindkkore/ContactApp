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


class CallFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {


    lateinit var callListAdapter: CallListAdapter
    lateinit var recyclerView: RecyclerView
    companion object {
        fun newInstance() = CallFragment()
        val TAG = "CallFragment"
        val URL_LOADER = 1
    }

    private lateinit var viewModel: CallViewModel
    val list = mutableListOf<CallModel>()

     lateinit var callLogsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.call_fragment, container, false)
        recyclerView=view.findViewById(R.id.recycler_view_call) as RecyclerView
        recyclerView.layoutManager =LinearLayoutManager(activity as Context)
        callListAdapter= CallListAdapter(this.activity as Context,list)
       recyclerView.adapter=callListAdapter
        callLogsTextView = view.findViewById<TextView>(R.id.test);
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CallViewModel::class.java)
        loaderManager.initLoader(URL_LOADER, null, this@CallFragment);
    }

/*private fun getCallDetails():String {

val sb = StringBuffer()
val managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null)
val number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
val type = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)
val duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
sb.append("Call Details :")
while (managedCursor.moveToNext())
{
val phNumber = managedCursor.getString(number)
val callType = managedCursor.getString(type)
val callDate = managedCursor.getString(date)
val callDayTime = Date(java.lang.Long.valueOf(callDate))
val callDuration = managedCursor.getString(duration)
var dir:String? = null
val dircode = Integer.parseInt(callType)
when (dircode) {
CallLog.Calls.OUTGOING_TYPE -> dir = "OUTGOING"

CallLog.Calls.INCOMING_TYPE -> dir = "INCOMING"

CallLog.Calls.MISSED_TYPE -> dir = "MISSED"
}
sb.append(
    "\nPhone Number:--- " + phNumber + " \nCall Type:--- "
    + dir + " \nCall Date:--- " + callDayTime
    + " \nCall duration in sec :--- " + callDuration
)
sb.append("\n----------------------------------")
}
managedCursor.close()
return sb.toString()

}*/


 override   fun onCreateLoader(loaderID: Int, args: Bundle?): Loader<Cursor> {
        list.clear()
        Log.d(TAG, "onCreateLoader() >> loaderID : $loaderID")



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
        Log.d(TAG, "onLoadFinished()")

        val sb = StringBuilder()

        val number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
        val type = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
        val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)
        val duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION)

      /*  sb.append("<h4>Call Log Details <h4>")
        sb.append("\n")
        sb.append("\n")

        sb.append("<table>")*/

        while (managedCursor.moveToNext()) {
            val callModel:CallModel = CallModel()
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

           /* sb.append("<tr>")
                .append("<td>Phone Number: </td>")
                .append("<td><strong>")
                .append(phNumber)
                .append("</strong></td>")
            sb.append("</tr>")
            sb.append("<br/>")
            sb.append("<tr>")
                .append("<td>Call Type:</td>")
                .append("<td><strong>")
                .append(dir)
                .append("</strong></td>")
            sb.append("</tr>")
            sb.append("<br/>")
            sb.append("<tr>")
                .append("<td>Date & Time:</td>")
                .append("<td><strong>")
                .append(callDayTime)
                .append("</strong></td>")
            sb.append("</tr>")
            sb.append("<br/>")
            sb.append("<tr>")
                .append("<td>Call Duration (Seconds):</td>")
                .append("<td><strong>")
                .append(callDuration)
                .append("</strong></td>")
            sb.append("</tr>")
            sb.append("<br/>")
            sb.append("<br/>")*/
            list.add(callModel)
        }
       // sb.append("</table>")

        managedCursor.close()

        //callLogsTextView.setText(Html.fromHtml(sb.toString()))
        callListAdapter.notifyDataSetChanged()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

