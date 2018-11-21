package com.test.contactapp

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.adapter.ContactAdapter
import com.test.contactapp.models.ContactDTO


class ContactFragmentNew : Fragment() {


   lateinit var recyclerView: RecyclerView
   val list = mutableListOf<ContactDTO>()
    lateinit var  adapter: ContactAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_blank, container, false)
        recyclerView= view.findViewById(R.id.recycler_view_contact)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter =ContactAdapter(this.activity!!,list)
        recyclerView.adapter=adapter
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getAllContacts();
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

    }



    companion object {

        val  TAG ="ContactFragmentNew"
        val TAG_ANDROID_CONTACTS = "ANDROID_CONTACTS"
    }

    /* Return all contacts and show each contact data in android monitor console as debug info. */
    private fun getAllContacts(): List<ContactDTO> {

        val ret = listOf<ContactDTO>()

        // Get all raw contacts id list.
        val rawContactsIdList = getRawContactsIdList()

        val contactListSize = rawContactsIdList.size

        val contentResolver = activity?.contentResolver

        // Loop in the raw contacts list.
        for (i in 0 until contactListSize) {
            // Get the raw contact id.
            val rawContactId = rawContactsIdList[i]

            Log.d(TAG_ANDROID_CONTACTS, "raw contact id : $rawContactId")

            // Data content uri (access data table. )
            val dataContentUri = ContactsContract.Data.CONTENT_URI

            // Build query columns name array.
            val queryColumnList = ArrayList<String>()

            // ContactsContract.Data.CONTACT_ID = "contact_id";
            queryColumnList.add(ContactsContract.Data.CONTACT_ID)

            // ContactsContract.Data.MIMETYPE = "mimetype";
            queryColumnList.add(ContactsContract.Data.MIMETYPE)

            queryColumnList.add(ContactsContract.Data.DATA1)
            queryColumnList.add(ContactsContract.Data.DATA2)
            queryColumnList.add(ContactsContract.Data.DATA3)
            queryColumnList.add(ContactsContract.Data.DATA4)
            queryColumnList.add(ContactsContract.Data.DATA5)
            queryColumnList.add(ContactsContract.Data.DATA6)
            queryColumnList.add(ContactsContract.Data.DATA7)
            queryColumnList.add(ContactsContract.Data.DATA8)
            queryColumnList.add(ContactsContract.Data.DATA9)
            queryColumnList.add(ContactsContract.Data.DATA10)
            queryColumnList.add(ContactsContract.Data.DATA11)
            queryColumnList.add(ContactsContract.Data.DATA12)
            queryColumnList.add(ContactsContract.Data.DATA13)
            queryColumnList.add(ContactsContract.Data.DATA14)
            queryColumnList.add(ContactsContract.Data.DATA15)

            // Translate column name list to array.
            val queryColumnArr = queryColumnList.toTypedArray()

            // Build query condition string. Query rows by contact id.
            val whereClauseBuf = StringBuffer()
            whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID)
            whereClauseBuf.append("=")
            whereClauseBuf.append(rawContactId)

            // Query data table and return related contact data.
            val cursor = contentResolver?.query(dataContentUri, queryColumnArr, whereClauseBuf.toString(), null, null)

            /* If this cursor return database table row data.
               If do not check cursor.getCount() then it will throw error
               android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0.
               */
            if (cursor != null && cursor!!.getCount() > 0) {
                val lineBuf = StringBuffer()
                cursor!!.moveToFirst()

                lineBuf.append("Raw Contact Id : ")
                lineBuf.append(rawContactId)

                val contactId = cursor!!.getLong(cursor!!.getColumnIndex(ContactsContract.Data.CONTACT_ID))
                lineBuf.append(" , Contact Id : ")
                lineBuf.append(contactId)

                do {
                    // First get mimetype column value.
                    val mimeType = cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.Data.MIMETYPE))
                    lineBuf.append(" \r\n , MimeType : ")
                    lineBuf.append(mimeType)

                    val dataValueList = getColumnValueByMimetype(cursor, mimeType)
                    val dataValueListSize = dataValueList.size
                    for (j in 0 until dataValueListSize) {
                        val dataValue = dataValueList[j]
                        lineBuf.append(" , ")
                        lineBuf.append(dataValue)
                    }

                } while (cursor!!.moveToNext())

                Log.d(TAG_ANDROID_CONTACTS, lineBuf.toString())
            }

            Log.d(TAG_ANDROID_CONTACTS, "=========================================================================")
        }
        list.addAll(ret)
        Log.e(TAG,"List Size ${list.size}")
        adapter.notifyDataSetChanged()
        return ret
    }

    /*
     *  Get email type related string format value.
     * */
    private fun getEmailTypeString(dataType: Int): String {
        var ret = ""

        if (ContactsContract.CommonDataKinds.Email.TYPE_HOME == dataType) {
            ret = "Home"
        } else if (ContactsContract.CommonDataKinds.Email.TYPE_WORK == dataType) {
            ret = "Work"
        }
        return ret
    }

    /*
     *  Get phone type related string format value.
     * */
    private fun getPhoneTypeString(dataType: Int): String {
        var ret = ""

        if (ContactsContract.CommonDataKinds.Phone.TYPE_HOME == dataType) {
            ret = "Home"
        } else if (ContactsContract.CommonDataKinds.Phone.TYPE_WORK == dataType) {
            ret = "Work"
        } else if (ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE == dataType) {
            ret = "Mobile"
        }
        return ret
    }

    /*
    *  Return data column value by mimetype column value.
    *  Because for each mimetype there has not only one related value,
    *  such as Organization.CONTENT_ITEM_TYPE need return company, department, title, job description etc.
    *  So the return is a list string, each string for one column value.
    * */
    private fun getColumnValueByMimetype(cursor: Cursor, mimeType: String): List<String> {
        val ret = ArrayList<String>()

        when (mimeType) {
            // Get email data.
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                // Email.ADDRESS == data1
                val emailAddress =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))
                // Email.TYPE == data2
                val emailType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))
                val emailTypeStr = getEmailTypeString(emailType)

                ret.add("Email Address : $emailAddress")
                ret.add("Email Int Type : $emailType")
                ret.add("Email String Type : $emailTypeStr")
            }

            // Get im data.
            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE -> {
                // Im.PROTOCOL == data5
                val imProtocol = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL))
                // Im.DATA == data1
                val imId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA))

                ret.add("IM Protocol : $imProtocol")
                ret.add("IM ID : $imId")
            }

            // Get nickname
            ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE -> {
                // Nickname.NAME == data1
                val nickName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME))
                ret.add("Nick name : $nickName")
            }

            // Get organization data.
            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE -> {
                // Organization.COMPANY == data1
                val company =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY))
                // Organization.DEPARTMENT == data5
                val department =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT))
                // Organization.TITLE == data4
                val title = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE))
                // Organization.JOB_DESCRIPTION == data6
                val jobDescription =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.JOB_DESCRIPTION))
                // Organization.OFFICE_LOCATION == data9
                val officeLocation =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.OFFICE_LOCATION))

                ret.add("Company : $company")
                ret.add("department : $department")
                ret.add("Title : $title")
                ret.add("Job Description : $jobDescription")
                ret.add("Office Location : $officeLocation")
            }

            // Get phone number.
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                // Phone.NUMBER == data1
                val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                // Phone.TYPE == data2
                val phoneTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                val phoneTypeStr = getPhoneTypeString(phoneTypeInt)

                ret.add("Phone Number : $phoneNumber")
                ret.add("Phone Type Integer : $phoneTypeInt")
                ret.add("Phone Type String : $phoneTypeStr")
            }

            // Get sip address.
            ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE -> {
                // SipAddress.SIP_ADDRESS == data1
                val address =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS))
                // SipAddress.TYPE == data2
                val addressTypeInt =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.SipAddress.TYPE))
                val addressTypeStr = getEmailTypeString(addressTypeInt)

                ret.add("Address : $address")
                ret.add("Address Type Integer : $addressTypeInt")
                ret.add("Address Type String : $addressTypeStr")
            }

            // Get display name.
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE -> {
                // StructuredName.DISPLAY_NAME == data1
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME))
                // StructuredName.GIVEN_NAME == data2
                val givenName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME))
                // StructuredName.FAMILY_NAME == data3
                val familyName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME))

                ret.add("Display Name : $displayName")
                ret.add("Given Name : $givenName")
                ret.add("Family Name : $familyName")
            }

            // Get postal address.
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE -> {
                // StructuredPostal.COUNTRY == data10
                val country =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY))
                // StructuredPostal.CITY == data7
                val city =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))
                // StructuredPostal.REGION == data8
                val region =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION))
                // StructuredPostal.STREET == data4
                val street =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))
                // StructuredPostal.POSTCODE == data9
                val postcode =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE))
                // StructuredPostal.TYPE == data2
                val postType =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE))
                val postTypeStr = getEmailTypeString(postType)

                ret.add("Country : $country")
                ret.add("City : $city")
                ret.add("Region : $region")
                ret.add("Street : $street")
                ret.add("Postcode : $postcode")
                ret.add("Post Type Integer : $postType")
                ret.add("Post Type String : $postTypeStr")
            }

            // Get identity.
            ContactsContract.CommonDataKinds.Identity.CONTENT_ITEM_TYPE -> {
                // Identity.IDENTITY == data1
                val identity =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.IDENTITY))
                // Identity.NAMESPACE == data2
                val namespace =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.NAMESPACE))

                ret.add("Identity : $identity")
                ret.add("Identity Namespace : $namespace")
            }

            // Get photo.
            ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE -> {
                // Photo.PHOTO == data15
                val photo = ""//cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO))
                // Photo.PHOTO_FILE_ID == data14
                val photoFileId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID))

                ret.add("Photo : $photo")
                ret.add("Photo File Id: $photoFileId")
            }

            // Get group membership.
            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE -> {
                // GroupMembership.GROUP_ROW_ID == data1
                val groupId =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID))
                ret.add("Group ID : $groupId")
            }

            // Get website.
            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE -> {
                // Website.URL == data1
                val websiteUrl = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL))
                // Website.TYPE == data2
                val websiteTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE))
                val websiteTypeStr = getEmailTypeString(websiteTypeInt)

                ret.add("Website Url : $websiteUrl")
                ret.add("Website Type Integer : $websiteTypeInt")
                ret.add("Website Type String : $websiteTypeStr")
            }

            // Get note.
            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE -> {
                // Note.NOTE == data1
                val note = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE))
                ret.add("Note : $note")
            }
        }

        return ret
    }

    // Return all raw_contacts _id in a list.
    private fun getRawContactsIdList(): List<Int> {
        val ret = ArrayList<Int>()

        val contentResolver = activity?.contentResolver

        // Row contacts content uri( access raw_contacts table. ).
        val rawContactUri = ContactsContract.RawContacts.CONTENT_URI
        // Return _id column in contacts raw_contacts table.
        val queryColumnArr = arrayOf(ContactsContract.RawContacts._ID)
        // Query raw_contacts table and return raw_contacts table _id.
        val cursor = contentResolver?.query(rawContactUri, queryColumnArr, null, null, null)
        if (cursor != null) {
            cursor!!.moveToFirst()
            do {
                val idColumnIndex = cursor!!.getColumnIndex(ContactsContract.RawContacts._ID)
                val rawContactsId = cursor!!.getInt(idColumnIndex)
                ret.add(rawContactsId)
            } while (cursor!!.moveToNext())
        }

        cursor!!.close()

        return ret
    }


    // Check whether user has phone contacts manipulation permission or not.
    private fun hasPhoneContactsPermission(permission: String): Boolean {
        var ret = false

        // If android sdk version is bigger than 23 the need to check run time permission.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // return phone read contacts permission grant status.
            val hasPermission = ContextCompat.checkSelfPermission(activity!!.applicationContext, permission)
            // If permission is granted then return true.
            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                ret = true
            }
        } else {
            ret = true
        }
        return ret
    }

    // Request a runtime permission to app user.
    private fun requestPermission(permission: String) {
        val requestPermissionArray = arrayOf(permission)
        ActivityCompat.requestPermissions(this.activity!!, requestPermissionArray, 1)
    }

    // After user select Allow or Deny button in request runtime permission dialog
    // , this method will be invoked.
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val length = grantResults.size
        if (length > 0) {
            val grantResult = grantResults[0]

            if (grantResult == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(
                    activity?.applicationContext,
                    "You allowed permission, please click the button again.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(activity?.applicationContext, "You denied permission.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
