package com.test.contactapp.data

import android.util.Log
import com.test.contactapp.data.objbox.Contact
import com.test.contactapp.data.objbox.Contact_
import io.objectbox.BoxStore
import io.objectbox.query.Query
import io.objectbox.rx.RxQuery
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject


class ObjectBoxDao (var boxStore: BoxStore)
{
   fun insertContacts(cantacts: List<Contact>): Boolean
   {
      try {
          val contactBox = boxStore.boxFor(Contact::class.java!!)
          contactBox.put(cantacts)
          return true
      }catch (e:Exception)
      {
          return false
      }

   }

    fun insertContact(cantact: Contact):Long
    {
        val contactBox = boxStore.boxFor(Contact::class.java!!)
        return contactBox.put(cantact)
    }

    fun updateContact(cantact: Contact):Long
    {
        val contactBox = boxStore.boxFor(Contact::class.java!!)
        return contactBox.put(cantact)
    }

    fun getAllContacts():MutableList<Contact>
    {
        val contactBx = boxStore.boxFor(Contact::class.java!!)
        return contactBx.all
    }

    /* Rx Version */
    fun getAllContacts1():Single<MutableList<Contact>>
    {
        val contactBx = boxStore.boxFor(Contact::class.java!!)
        val query = contactBx.query().build();
        return RxQuery.single(query);
    }

    fun getContact( contactId: Long?):Contact
    {
        val contactBox = boxStore.boxFor(Contact::class.java!!)
        val contact =contactBox.get(contactId!!)
        Log.e("TAG",contact.first_name)
        return contact;//contactBox.get(contactId!!)
    }




}