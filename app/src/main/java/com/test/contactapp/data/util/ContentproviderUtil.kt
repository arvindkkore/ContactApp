package com.test.contactapp.data.util

import android.content.Context
import android.provider.CallLog
import android.util.Log
import com.test.contactapp.data.models.CallModel
import java.lang.Long
import java.util.*

fun fetchData(context: Context): MutableList<CallModel> {
    val list = mutableListOf<CallModel>()
    val uri = CallLog.Calls.CONTENT_URI // Contact URI
    val contactsCursor = context.contentResolver?.query(
        uri,
        null, null, null, null
    )
    contactsCursor?.let { cursor ->
        val name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
        val number = cursor.getColumnIndex(CallLog.Calls.NUMBER)
        val type = cursor.getColumnIndex(CallLog.Calls.TYPE)
        val date = cursor.getColumnIndex(CallLog.Calls.DATE)
        val duration = cursor.getColumnIndex(CallLog.Calls.DURATION)
        cursor.moveToFirst()
        while (contactsCursor.moveToNext()) {
            val callModel: CallModel = CallModel()
            val phName = cursor.getString(name)
            callModel.contactName = phName
            val phNumber = cursor.getString(number)
            callModel.contactNumber = phNumber
            val callType = cursor.getString(type)
            val callDate = cursor.getString(date)
            callModel.callDate = callDate
            val callDayTime = Date(Long.valueOf(callDate))
            callModel.callDayTime = callDayTime
            val callDuration = cursor.getString(duration)
            callModel.duration = callDuration
            var dir: String? = null

            val callTypeCode = Integer.parseInt(callType)
            when (callTypeCode) {
                CallLog.Calls.OUTGOING_TYPE -> callModel.type = "Outgoing"

                CallLog.Calls.INCOMING_TYPE -> callModel.type = "Incoming"

                CallLog.Calls.MISSED_TYPE -> callModel.type = "Missed"
            }


            list.add(callModel)
        }

        Log.e("CallLogModel", "contact size${list.size}")
        cursor.close()

    }
    return list

}