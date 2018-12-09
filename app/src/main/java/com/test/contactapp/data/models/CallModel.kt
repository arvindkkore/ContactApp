package com.test.contactapp.data.models

import java.util.*

class CallModel
{
    var type:String=""
    var contactNumber:String=""
    var contactName:String?=""
    var duration:String=""
    var callDate :String= "";
    var callDayTime:Date? =null

    var photo:String? =null
    override fun toString(): String {
        return "type ${type} number ${contactNumber} name ${contactName} photo ${photo}"
    }

}