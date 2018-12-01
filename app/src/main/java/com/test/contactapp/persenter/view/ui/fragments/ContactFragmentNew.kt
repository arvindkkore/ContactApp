package com.test.contactapp.persenter.view.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.R
import com.test.contactapp.persenter.adapter.CustomAdapter
import com.test.contactapp.data.models.ContactDTO
import com.test.contactapp.data.models.Contact_Model
import com.test.contactapp.persenter.view.ui.activity.ContactDetailActivity

import java.io.File
import java.io.FileOutputStream
import java.text.FieldPosition


class ContactFragmentNew : Fragment(), OnItemClick {


    lateinit var recyclerView: RecyclerView
    val list = mutableListOf<Contact_Model>()
    lateinit var adapter: CustomAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_contact)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CustomAdapter(this.activity as Context, list,this)
        recyclerView.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getAllContacts();
        LoadContacts().execute()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

    }

    override fun onClick(position: Int){
        Intent(activity,ContactDetailActivity::class.java).apply { startActivity(this) }
    }
    companion object {

        val TAG = "ContactFragmentNew"
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
        //list.addAll(ret)
        Log.e(TAG, "List Size ${list.size}")
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

    // Async task to load contacts
    private inner class LoadContacts : AsyncTask<Void?, Void?, Void?>() {

        var arrayList = mutableListOf<Contact_Model>()
        override fun doInBackground(vararg params: Void?): Void? {
            arrayList = readContacts()// Get contacts array list from this
            // method
            return null
        }

        override fun onPostExecute(result: Void?) {

            super.onPostExecute(result)

            // If array list is not null and is contains value
            if (arrayList != null && arrayList.size > 0) {

                /*// then set total contacts to subtitle
                getSupportActionBar().setSubtitle(
                    arrayList!!.size.toString() + " Contacts"
                )*/

                list.addAll(arrayList)
                adapter.notifyDataSetChanged()
            } else {

                // If adapter is null then show toast
                Toast.makeText(
                    activity!!, "There are no contacts.",
                    Toast.LENGTH_LONG
                ).show()
            }

            // Hide dialog if showing
            /* if (pd.isShowing())
                pd.dismiss();*/

        }

        override fun onPreExecute() {

            super.onPreExecute()
            // Show Dialog
            /*   pd = ProgressDialog.show(MainActivity.this, "Loading Contacts",
                    "Please Wait...");*/
        }

    }

   /* private fun readContacts(): MutableList<Contact_Model> {
        val contactList = mutableListOf<Contact_Model>()
        val uri = ContactsContract.Contacts.CONTENT_URI // Contact URI
        val contactsCursor = this.activity?.contentResolver?.query(
            uri,
            null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC "
        ) // Return
        // all
        // contacts
        // name
        // containing
        // in
        // URI
        // in
        // ascending
        // order
        // Move cursor at starting
        contactsCursor?.let {

            do {
                val contctId = contactsCursor.getLong(
                    contactsCursor
                        .getColumnIndex("_ID")
                ) // Get contact ID
                val dataUri = ContactsContract.Data.CONTENT_URI // URI to get
                // data of
                // contacts
                val dataCursor = this.activity?.contentResolver?.query(
                    dataUri, null,
                    ContactsContract.Data.CONTACT_ID + " = " + contctId, null, null
                )// Retrun data cusror represntative to
                // contact ID

                // Strings to get all details
                var displayName = ""
                var nickName = ""
                var homePhone = ""
                var mobilePhone = ""
                var workPhone = ""
                var photoPath = "" + R.drawable.img_avatar // Photo path
                var photoByte: ByteArray? = null// Byte to get photo since it will come
                // in BLOB
                var homeEmail = ""
                var workEmail = ""
                var companyName = ""
                var title = ""

                // This strings stores all contact numbers, email and other
                // details like nick name, company etc.
                var contactNumbers = ""
                var contactEmailAddresses = ""
                var contactOtherDetails = ""

                // Now start the cusrsor
                if (dataCursor!!.moveToFirst()) {
                    displayName = dataCursor
                        .getString(
                            dataCursor
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )// get
                    // the
                    // contact
                    // name
                    do {
                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE
                        ) {
                            nickName = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data1")
                            ) // Get Nick Name
                            contactOtherDetails += ("NickName : " + nickName
                                    + "n")// Add the nick name to string

                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                        ) {

                            // In this get All contact numbers like home,
                            // mobile, work, etc and add them to numbers string
                            when (dataCursor.getInt(
                                dataCursor
                                    .getColumnIndex("data2")
                            )) {
                                ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> {
                                    homePhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Home Phone : " + homePhone
                                            + "n")
                                }

                                ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> {
                                    workPhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Work Phone : " + workPhone
                                            + "n")
                                }

                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> {
                                    mobilePhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Mobile Phone : "
                                            + mobilePhone + "n")
                                }
                            }
                        }
                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                        ) {

                            // In this get all Emails like home, work etc and
                            // add them to email string
                            when (dataCursor.getInt(
                                dataCursor
                                    .getColumnIndex("data2")
                            )) {
                                ContactsContract.CommonDataKinds.Email.TYPE_HOME -> {
                                    homeEmail = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactEmailAddresses += ("Home Email : "
                                            + homeEmail + "n")
                                }
                                ContactsContract.CommonDataKinds.Email.TYPE_WORK -> {
                                    workEmail = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactEmailAddresses += ("Work Email : "
                                            + workEmail + "n")
                                }
                            }
                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                        ) {
                            companyName = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data1")
                            )// get company
                            // name
                            contactOtherDetails += ("Coompany Name : "
                                    + companyName + "n")
                            title = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data4")
                            )// get Company
                            // title
                            contactOtherDetails += "Title : " + title + "n"

                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                        ) {
                            photoByte = dataCursor.getBlob(
                                dataCursor
                                    .getColumnIndex("data15")
                            ) // get photo in
                            // byte

                            if (photoByte != null) {

                                // Now make a cache folder in file manager to
                                // make cache of contacts images and save them
                                // in .png
                                val bitmap = BitmapFactory.decodeByteArray(
                                    photoByte, 0, photoByte!!.size
                                )
                                val cacheDirectory = this.activity?.baseContext?.cacheDir
                                val tmp = File(
                                    cacheDirectory?.path
                                            + "/_androhub" + contctId + ".png"
                                )
                                try {
                                    val fileOutputStream = FileOutputStream(
                                        tmp
                                    )
                                    bitmap.compress(
                                        Bitmap.CompressFormat.PNG,
                                        100, fileOutputStream
                                    )
                                    fileOutputStream.flush()
                                    fileOutputStream.close()
                                } catch (e: Exception) {
                                    // TODO: handle exception
                                    e.printStackTrace()
                                }

                                photoPath = tmp.getPath()// finally get the
                                // saved path of
                                // image
                            }

                        }

                    } while (dataCursor.moveToNext()) // Now move to next
                    // cursor

                    contactList.add(
                        Contact_Model(
                            java.lang.Long.toString(contctId),
                            displayName, contactNumbers, contactEmailAddresses,
                            photoPath, contactOtherDetails
                        )
                    )// Finally add
                    // items to
                    // array list
                }

            } while (contactsCursor.moveToNext())

        }
        if (contactsCursor.moveToFirst()) {
            do {
                val contctId = contactsCursor.getLong(
                    contactsCursor
                        .getColumnIndex("_ID")
                ) // Get contact ID
                val dataUri = ContactsContract.Data.CONTENT_URI // URI to get
                // data of
                // contacts
                val dataCursor = this.activity?.contentResolver?.query(
                    dataUri, null,
                    ContactsContract.Data.CONTACT_ID + " = " + contctId, null, null
                )// Retrun data cusror represntative to
                // contact ID

                // Strings to get all details
                var displayName = ""
                var nickName = ""
                var homePhone = ""
                var mobilePhone = ""
                var workPhone = ""
                var photoPath = "" + R.drawable.img_avatar // Photo path
                var photoByte: ByteArray? = null// Byte to get photo since it will come
                // in BLOB
                var homeEmail = ""
                var workEmail = ""
                var companyName = ""
                var title = ""

                // This strings stores all contact numbers, email and other
                // details like nick name, company etc.
                var contactNumbers = ""
                var contactEmailAddresses = ""
                var contactOtherDetails = ""

                // Now start the cusrsor
                if (dataCursor!!.moveToFirst()) {
                    displayName = dataCursor
                        .getString(
                            dataCursor
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )// get
                    // the
                    // contact
                    // name
                    do {
                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE
                        ) {
                            nickName = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data1")
                            ) // Get Nick Name
                            contactOtherDetails += ("NickName : " + nickName
                                    + "n")// Add the nick name to string

                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                        ) {

                            // In this get All contact numbers like home,
                            // mobile, work, etc and add them to numbers string
                            when (dataCursor.getInt(
                                dataCursor
                                    .getColumnIndex("data2")
                            )) {
                                ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> {
                                    homePhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Home Phone : " + homePhone
                                            + "n")
                                }

                                ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> {
                                    workPhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Work Phone : " + workPhone
                                            + "n")
                                }

                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> {
                                    mobilePhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Mobile Phone : "
                                            + mobilePhone + "n")
                                }
                            }
                        }
                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                        ) {

                            // In this get all Emails like home, work etc and
                            // add them to email string
                            when (dataCursor.getInt(
                                dataCursor
                                    .getColumnIndex("data2")
                            )) {
                                ContactsContract.CommonDataKinds.Email.TYPE_HOME -> {
                                    homeEmail = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactEmailAddresses += ("Home Email : "
                                            + homeEmail + "n")
                                }
                                ContactsContract.CommonDataKinds.Email.TYPE_WORK -> {
                                    workEmail = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactEmailAddresses += ("Work Email : "
                                            + workEmail + "n")
                                }
                            }
                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                        ) {
                            companyName = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data1")
                            )// get company
                            // name
                            contactOtherDetails += ("Coompany Name : "
                                    + companyName + "n")
                            title = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data4")
                            )// get Company
                            // title
                            contactOtherDetails += "Title : " + title + "n"

                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                        ) {
                            photoByte = dataCursor.getBlob(
                                dataCursor
                                    .getColumnIndex("data15")
                            ) // get photo in
                            // byte

                            if (photoByte != null) {

                                // Now make a cache folder in file manager to
                                // make cache of contacts images and save them
                                // in .png
                                val bitmap = BitmapFactory.decodeByteArray(
                                    photoByte, 0, photoByte!!.size
                                )
                                val cacheDirectory = this.activity?.baseContext?.cacheDir
                                val tmp = File(
                                    cacheDirectory?.path
                                            + "/_androhub" + contctId + ".png"
                                )
                                try {
                                    val fileOutputStream = FileOutputStream(
                                        tmp
                                    )
                                    bitmap.compress(
                                        Bitmap.CompressFormat.PNG,
                                        100, fileOutputStream
                                    )
                                    fileOutputStream.flush()
                                    fileOutputStream.close()
                                } catch (e: Exception) {
                                    // TODO: handle exception
                                    e.printStackTrace()
                                }

                                photoPath = tmp.getPath()// finally get the
                                // saved path of
                                // image
                            }

                        }

                    } while (dataCursor.moveToNext()) // Now move to next
                    // cursor

                    contactList.add(
                        Contact_Model(
                            java.lang.Long.toString(contctId),
                            displayName, contactNumbers, contactEmailAddresses,
                            photoPath, contactOtherDetails
                        )
                    )// Finally add
                    // items to
                    // array list
                }

            } while (contactsCursor.moveToNext())
        }
        return contactList
    }*/


    private fun readContacts(): MutableList<Contact_Model> {
        val contactList = mutableListOf<Contact_Model>()

        val uri = ContactsContract.Contacts.CONTENT_URI // Contact URI
        val contactsCursor = this.activity?.contentResolver?.query(
            uri,
            null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC "
        ) // Return
        // all
        // contacts
        // name
        // containing
        // in
        // URI
        // in
        // ascending
        // order
        // Move cursor at starting

        if(contactsCursor !=null && contactsCursor.moveToFirst())
        {
           do {
               val contctId = contactsCursor.getLong(
                   contactsCursor
                       .getColumnIndex("_ID")
               ) // Get contact ID
               val dataUri = ContactsContract.Data.CONTENT_URI // URI to get
               // data of
               // contacts
               val dataCursor = this.activity?.contentResolver?.query(
                   dataUri, null,
                   ContactsContract.Data.CONTACT_ID + " = " + contctId, null, null
               )// Retrun data cusror represntative to
               // contact ID
               // Strings to get all details
               var displayName:String? =null
               var nickName :String? =null
               var homePhone :String? =null
               var mobilePhone :String? =null
               var workPhone :String? =null
               var photoPath = "" + R.drawable.img_avatar // Photo path
               var photoByte: ByteArray? = null// Byte to get photo since it will come
               // in BLOB
               var homeEmail :String? =null
               var workEmail:String? =null
               var companyName:String? =null
               var title:String? =null

               // This strings stores all contact numbers, email and other
               // details like nick name, company etc.
               var contactNumbers :String? =null
               var contactEmailAddresses :String? =null
               var contactOtherDetails:String? =null


               // Now start the cusrsor
               if (dataCursor != null && dataCursor.moveToFirst()) {
                   displayName = dataCursor
                       .getString(
                           dataCursor
                               .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                       )// get
                   // the
                   // contact
                   // name
                   do {
                       if (dataCursor
                               .getString(
                                   dataCursor.getColumnIndex("mimetype")
                               ) == ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE
                       ) {
                           nickName = dataCursor.getString(
                               dataCursor
                                   .getColumnIndex("data1")
                           ) // Get Nick Name
                           contactOtherDetails += ("NickName : " + nickName
                                   + "n")// Add the nick name to string

                       }

                       if (dataCursor
                               .getString(
                                   dataCursor.getColumnIndex("mimetype")
                               ) == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                       ) {

                           // In this get All contact numbers like home,
                           // mobile, work, etc and add them to numbers string
                           when (dataCursor.getInt(
                               dataCursor
                                   .getColumnIndex("data2")
                           )) {
                               ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> {
                                   homePhone = dataCursor.getString(
                                       dataCursor
                                           .getColumnIndex("data1")
                                   )
                                   contactNumbers += ("Home Phone : " + homePhone
                                           + "n")
                               }

                               ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> {
                                   workPhone = dataCursor.getString(
                                       dataCursor
                                           .getColumnIndex("data1")
                                   )
                                   contactNumbers += ("Work Phone : " + workPhone
                                           + "n")
                               }

                               ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> {
                                   mobilePhone = dataCursor.getString(
                                       dataCursor
                                           .getColumnIndex("data1")
                                   )
                                   contactNumbers += ("Mobile Phone : "
                                           + mobilePhone + "n")
                               }
                           }
                       }
                       if (dataCursor
                               .getString(
                                   dataCursor.getColumnIndex("mimetype")
                               ) == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                       ) {

                           // In this get all Emails like home, work etc and
                           // add them to email string
                           when (dataCursor.getInt(
                               dataCursor
                                   .getColumnIndex("data2")
                           )) {
                               ContactsContract.CommonDataKinds.Email.TYPE_HOME -> {
                                   homeEmail = dataCursor.getString(
                                       dataCursor
                                           .getColumnIndex("data1")
                                   )
                                   contactEmailAddresses += ("Home Email : "
                                           + homeEmail + "n")
                               }
                               ContactsContract.CommonDataKinds.Email.TYPE_WORK -> {
                                   workEmail = dataCursor.getString(
                                       dataCursor
                                           .getColumnIndex("data1")
                                   )
                                   contactEmailAddresses += ("Work Email : "
                                           + workEmail + "n")
                               }
                           }
                       }

                       if (dataCursor
                               .getString(
                                   dataCursor.getColumnIndex("mimetype")
                               ) == ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                       ) {
                           companyName = dataCursor.getString(
                               dataCursor
                                   .getColumnIndex("data1")
                           )// get company
                           // name
                           contactOtherDetails += ("Coompany Name : "
                                   + companyName + "n")
                           title = dataCursor.getString(
                               dataCursor
                                   .getColumnIndex("data4")
                           )// get Company
                           // title
                           contactOtherDetails += "Title : " + title + "n"

                       }

                       if (dataCursor
                               .getString(
                                   dataCursor.getColumnIndex("mimetype")
                               ) == ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                       ) {
                           photoByte = dataCursor.getBlob(
                               dataCursor
                                   .getColumnIndex("data15")
                           ) // get photo in
                           // byte

                           if (photoByte != null) {

                               // Now make a cache folder in file manager to
                               // make cache of contacts images and save them
                               // in .png
                               val bitmap = BitmapFactory.decodeByteArray(
                                   photoByte, 0, photoByte!!.size
                               )
                               val cacheDirectory = this.activity!!.baseContext
                                   .getCacheDir()
                               val tmp = File(
                                   cacheDirectory.getPath()
                                           + "/_androhub" + contctId + ".png"
                               )
                               try {
                                   val fileOutputStream = FileOutputStream(
                                       tmp
                                   )
                                   bitmap.compress(
                                       Bitmap.CompressFormat.PNG,
                                       100, fileOutputStream
                                   )
                                   fileOutputStream.flush()
                                   fileOutputStream.close()
                               } catch (e: Exception) {
                                   // TODO: handle exception
                                   e.printStackTrace()
                               }

                               photoPath = tmp.getPath()// finally get the
                               // saved path of
                               // image
                           }

                       }


                   }while (dataCursor.moveToNext())
               }

               contactList.add(
                   Contact_Model(
                       java.lang.Long.toString(contctId),
                       displayName, contactNumbers, contactEmailAddresses,
                       photoPath, contactOtherDetails
                   )
               )// Finally add
               // items to
               // array list
           } while (contactsCursor.moveToNext())
            // cursor


        }








        /*if (contactsCursor.moveToFirst()) {
            do {
                val contctId = contactsCursor.getLong(
                    contactsCursor
                        .getColumnIndex("_ID")
                ) // Get contact ID
                val dataUri = ContactsContract.Data.CONTENT_URI // URI to get
                // data of
                // contacts
                val dataCursor = getContentResolver().query(
                    dataUri, null,
                    ContactsContract.Data.CONTACT_ID + " = " + contctId, null, null
                )// Retrun data cusror represntative to
                // contact ID

                // Strings to get all details
                var displayName = ""
                var nickName = ""
                var homePhone = ""
                var mobilePhone = ""
                var workPhone = ""
                var photoPath = "" + R.drawable.lady // Photo path
                var photoByte: ByteArray? = null// Byte to get photo since it will come
                // in BLOB
                var homeEmail = ""
                var workEmail = ""
                var companyName = ""
                var title = ""

                // This strings stores all contact numbers, email and other
                // details like nick name, company etc.
                var contactNumbers = ""
                var contactEmailAddresses = ""
                var contactOtherDetails = ""

                // Now start the cusrsor
                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                        .getString(
                            dataCursor
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )// get
                    // the
                    // contact
                    // name
                    do {
                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE
                        ) {
                            nickName = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data1")
                            ) // Get Nick Name
                            contactOtherDetails += ("NickName : " + nickName
                                    + "n")// Add the nick name to string

                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                        ) {

                            // In this get All contact numbers like home,
                            // mobile, work, etc and add them to numbers string
                            when (dataCursor.getInt(
                                dataCursor
                                    .getColumnIndex("data2")
                            )) {
                                ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> {
                                    homePhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Home Phone : " + homePhone
                                            + "n")
                                }

                                ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> {
                                    workPhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Work Phone : " + workPhone
                                            + "n")
                                }

                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> {
                                    mobilePhone = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactNumbers += ("Mobile Phone : "
                                            + mobilePhone + "n")
                                }
                            }
                        }
                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                        ) {

                            // In this get all Emails like home, work etc and
                            // add them to email string
                            when (dataCursor.getInt(
                                dataCursor
                                    .getColumnIndex("data2")
                            )) {
                                ContactsContract.CommonDataKinds.Email.TYPE_HOME -> {
                                    homeEmail = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactEmailAddresses += ("Home Email : "
                                            + homeEmail + "n")
                                }
                                ContactsContract.CommonDataKinds.Email.TYPE_WORK -> {
                                    workEmail = dataCursor.getString(
                                        dataCursor
                                            .getColumnIndex("data1")
                                    )
                                    contactEmailAddresses += ("Work Email : "
                                            + workEmail + "n")
                                }
                            }
                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                        ) {
                            companyName = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data1")
                            )// get company
                            // name
                            contactOtherDetails += ("Coompany Name : "
                                    + companyName + "n")
                            title = dataCursor.getString(
                                dataCursor
                                    .getColumnIndex("data4")
                            )// get Company
                            // title
                            contactOtherDetails += "Title : " + title + "n"

                        }

                        if (dataCursor
                                .getString(
                                    dataCursor.getColumnIndex("mimetype")
                                ) == ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                        ) {
                            photoByte = dataCursor.getBlob(
                                dataCursor
                                    .getColumnIndex("data15")
                            ) // get photo in
                            // byte

                            if (photoByte != null) {

                                // Now make a cache folder in file manager to
                                // make cache of contacts images and save them
                                // in .png
                                val bitmap = BitmapFactory.decodeByteArray(
                                    photoByte, 0, photoByte!!.size
                                )
                                val cacheDirectory = getBaseContext()
                                    .getCacheDir()
                                val tmp = File(
                                    cacheDirectory.getPath()
                                            + "/_androhub" + contctId + ".png"
                                )
                                try {
                                    val fileOutputStream = FileOutputStream(
                                        tmp
                                    )
                                    bitmap.compress(
                                        Bitmap.CompressFormat.PNG,
                                        100, fileOutputStream
                                    )
                                    fileOutputStream.flush()
                                    fileOutputStream.close()
                                } catch (e: Exception) {
                                    // TODO: handle exception
                                    e.printStackTrace()
                                }

                                photoPath = tmp.getPath()// finally get the
                                // saved path of
                                // image
                            }

                        }

                    } while (dataCursor.moveToNext()) // Now move to next
                    // cursor

                    contactList.add(
                        Contact_Model(
                            java.lang.Long.toString(contctId),
                            displayName, contactNumbers, contactEmailAddresses,
                            photoPath, contactOtherDetails
                        )
                    )// Finally add
                    // items to
                    // array list
                }

            } while (contactsCursor.moveToNext())
        }*/
        return contactList
    }

}

interface OnItemClick{
    fun onClick(position:Int)
}
