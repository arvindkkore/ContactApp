package com.test.contactapp.persenter.view.ui.fragments

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.test.contactapp.R
import com.test.contactapp.persenter.view.viewmodel.ContactViewModel

@Deprecated(message ="ContactFragment is used instead")
class ContactFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>
{

    var mContactId: Long = 0

    // A content URI for the selected contact
    var mContactUri: Uri? = null
    // An adapter that binds the result Cursor to the ListView
    private var mCursorAdapter: SimpleCursorAdapter? = null
    private var mSearchString: String? = null
    private val mSelectionArgs: Array<String> = arrayOf("")
    // Define a ListView object
    lateinit var mContactsList: ListView
    private val TO_IDS: IntArray = intArrayOf(R.id.cantact_number)


    // Defines the text expression
    @SuppressLint("InlinedApi")
    private val SELECTION: String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE ?"
        else
            "${ContactsContract.Contacts.DISPLAY_NAME} LIKE ?"

    @SuppressLint("InlinedApi")
    private val PROJECTION: Array<out String> = arrayOf(
        /*
         * The detail data row ID. To make a ListView work,
         * this column is required.
         */
        ContactsContract.Data._ID,
        // The primary display name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            ContactsContract.Data.DISPLAY_NAME_PRIMARY
        else
            ContactsContract.Data.DISPLAY_NAME,
        // The contact's _ID, to construct a content URI
        ContactsContract.Data.CONTACT_ID,
        // The contact's LOOKUP_KEY, to construct a content URI
        ContactsContract.Data.LOOKUP_KEY
    )


    companion object {
        fun newInstance() = ContactFragment()
    }

    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)


            // Gets the ListView from the View list of the parent activity
            activity?.also {

                mContactsList = it.findViewById<ListView>(R.id.recycler_view_contact)
                // Gets a CursorAdapter
                mCursorAdapter = SimpleCursorAdapter(
                    it,
                    R.layout.item_contact,
                    null,
                    FROM_COLUMNS, TO_IDS,
                    0
                )
                // Sets the adapter for the ListView
                mContactsList.adapter = mCursorAdapter
            }

        loaderManager.initLoader(0, null, this)
    }

    @SuppressLint("InlinedApi")
    private val FROM_COLUMNS: Array<String> = arrayOf(
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        } else {
            ContactsContract.Contacts.DISPLAY_NAME
        }
    )

    override fun onCreateLoader(loaderId: Int, args: Bundle?): Loader<Cursor> {
        mSelectionArgs[0] = "%$mSearchString%"
        // Starts the query
        return activity?.let {
            return CursorLoader(
                it,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
            )
        } ?: throw IllegalStateException()

    }



    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        // Put the result Cursor in the adapter for the ListView
        mCursorAdapter?.swapCursor(cursor)
    }
    override fun onLoaderReset(loader: Loader<Cursor>) {
        // Delete the reference to the existing Cursor
        mCursorAdapter?.swapCursor(null)
    }
}
