package com.test.contactapp.data.util

import android.content.Context
import android.provider.CallLog
import android.provider.ContactsContract
import android.util.Log
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.data.objbox.*
import java.lang.Long
import java.util.Date

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
        val photo = cursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_URI)
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
            callModel.photo = cursor.getString(photo)
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

fun readContacts(context: Context):MutableList<Contact> {

    val contactList = mutableListOf<Contact>()

    val cr = context.contentResolver
    val cur = cr?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

    if (cur!!.getCount() > 0) {
        while (cur!!.moveToNext()) {
            val contact = Contact() //contact
            val id = cur!!.getString(cur!!.getColumnIndex(ContactsContract.Contacts._ID))
            val name = cur!!.getString(cur!!.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

            contact.first_name = name
            if (Integer.parseInt(cur!!.getString(cur!!.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                println("name : $name, ID : $id")

                // get the phone number
                val pCur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf<String>(id), null
                )
                val phoneList = mutableListOf<Phone>()
                while (pCur!!.moveToNext()) {
                    val phoneObjr = Phone() //phone
                    val phone = pCur!!.getString(
                        pCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                    phoneObjr.phone_number = phone
                    val type = pCur!!.getInt(
                        pCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)

                    )
                    val phonetype = PhoneTypeLookup()
                    phonetype.id = type.toLong()
                    if (ContactsContract.CommonDataKinds.Phone.TYPE_HOME == type) {
                        phonetype.phoneType = "Home"
                    } else if (ContactsContract.CommonDataKinds.Phone.TYPE_WORK == type) {
                        phonetype.phoneType = "Work"
                    } else if (ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE == type) {
                        phonetype.phoneType = "Mobile"
                    }
                    phoneObjr.phone_type.target = phonetype
                    contact.phones.add(phoneObjr)
                }
                pCur!!.close()


                // get email and type

                val emailCur = cr.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                    arrayOf<String>(id), null
                )
                while (emailCur!!.moveToNext()) {
                    val emailobj = Email()
                    // This would allow you get several email addresses
                    // if the email addresses were stored in an array
                    val email = emailCur!!.getString(
                        emailCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                    )
                    val emailType = emailCur!!.getInt(
                        emailCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)
                    )
                    emailobj.email_address = email
                    val emailTypeLookup = EmailTypeLookup()

                    emailTypeLookup.id = emailType.toLong()
                    emailTypeLookup.emailType = when (emailType) {
                        ContactsContract.CommonDataKinds.Email.TYPE_HOME -> "Home"
                        ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> "Mobile"
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK -> "Work"
                        ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM -> "Custom"
                        else -> "Other"
                    }
                    emailobj.emailType.target = emailTypeLookup
                    contact.emails.add(emailobj)
                    println("Email $email Email Type : $emailType")
                }
                emailCur!!.close()

                // Get note.......
                val noteWhere =
                    ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                val noteWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                val noteCur = cr?.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null)
                if (noteCur!!.moveToFirst()) {
                    val note =
                        noteCur!!.getString(noteCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE))
                    contact.note = note
                    println("Note $note")
                }
                noteCur!!.close()

                //Get Postal Address....

                val addrWhere =
                    ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                val addrWhereParams =
                    arrayOf(id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                val addrCur = cr.query(ContactsContract.Data.CONTENT_URI, null, null, null, null)
                while (addrCur!!.moveToNext()) {
                    val address = Address()
                    val poBox = addrCur!!.getString(
                        addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX)
                    )

                    val street = addrCur!!.getString(
                        addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)
                    )
                    address.address = street
                    val city = addrCur!!.getString(
                        addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)
                    )
                    address.city = city
                    val state = addrCur!!.getString(
                        addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)
                    )
                    address.state = state
                    val postalCode = addrCur!!.getString(
                        addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)
                    )
                    address.postalcode = postalCode
                    val country = addrCur!!.getString(
                        addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)
                    )
                    address.country = country


                    val type = addrCur!!.getInt(
                        addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE)
                    )
                    val addressTypeLookup = AddressTypeLookup(type.toLong())
                    addressTypeLookup.addressType = when (type) {
                        ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME -> "Home"
                        ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK -> "Work"
                        ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM -> "Custom"
                        ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER -> "Other"
                        else -> "Other"
                    }


                    address.addressType.target = addressTypeLookup
                    contact.addresses.add(address)
                }
                addrCur!!.close()


                // Get website .......
               /* val websiteWhere =
                    ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                val websiteWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                val websiteCur = cr?.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null)
                if (websiteCur!!.moveToNext()) {
                    val websiteadd =
                        websiteCur!!.getString(websiteCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Website.))

                    contact.note = note
                    println("Note $note")
                }*/

                val phoneWhere =
                    ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                val phoneWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                val photoCur = cr?.query(ContactsContract.Data.CONTENT_URI, null, phoneWhere, phoneWhereParams, null)
                if (photoCur!!.moveToFirst()) {

                        val note =
                            noteCur!!.getString(noteCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE))
                        contact.note = note
                    var photoByte: ByteArray?= photoCur.getBlob(
                        photoCur  .getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO)
                    )

                    }





                // Get Instant Messenger.........
                val imWhere =
                    ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                val imWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                val imCur = cr.query(ContactsContract.Data.CONTENT_URI, null, imWhere, imWhereParams, null)
                if (imCur!!.moveToFirst()) {
                    val imName = imCur!!.getString(
                        imCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA)
                    )
                    val imType: String
                    imType = imCur!!.getString(
                        imCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE)
                    )
                }
                imCur!!.close()


                // Get Organizations.........

                val orgWhere =
                    ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                val orgWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                val orgCur = cr.query(ContactsContract.Data.CONTENT_URI, null, orgWhere, orgWhereParams, null)
                if (orgCur!!.moveToFirst()) {
                    val orgName =
                        orgCur!!.getString(orgCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA))
                    contact.companyName = orgName
                    val title =
                        orgCur!!.getString(orgCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE))
                    contact.designation = title
                }
                orgCur!!.close()
            }
            contactList.add(contact)
        }
    }
    return contactList
}
