package com.test.contactapp.data

import android.util.Log
import com.test.contactapp.data.objbox.*
import io.objectbox.BoxStore
import io.objectbox.rx.RxQuery
import io.reactivex.Single
import java.lang.Exception


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

    fun insertPhoneLookUp(phones: MutableList<PhoneTypeLookup>):Boolean {
        try {
            val phoneTypeBox = boxStore.boxFor(PhoneTypeLookup::class.java!!)
            phoneTypeBox.put(phones)
            return true
        }catch (e:Exception)
        {
            return false
        }
    }

    fun insertEmailLookup(emails: MutableList<EmailTypeLookup>):Boolean {
        try {
            val phoneTypeBox = boxStore.boxFor(EmailTypeLookup::class.java!!)
            phoneTypeBox.put(emails)
            return true
        }catch (e:Exception)
        {
            return false
        }
    }

    fun insertAddressLookup(addresses: MutableList<AddressTypeLookup>): Boolean {
        try {
            val addressTypeLookup = boxStore.boxFor(AddressTypeLookup::class.java!!)
            addressTypeLookup.put(addresses)
            return true
        }catch (e:Exception)
        {
            return false
        }
    }

    fun insertDateLookup(dates: MutableList<DateTypeLookup>):Boolean {
        try {
            val dateTypeLookup = boxStore.boxFor(DateTypeLookup::class.java!!)
            dateTypeLookup.put(dates)
            return true
        }catch (e:Exception)
        {
            return false
        }
    }

    fun getPhoneLookUp(): MutableList<PhoneTypeLookup> {


        val contactBx = boxStore.boxFor(PhoneTypeLookup::class.java!!)
        return contactBx.all
    }

    fun getEmailLookUp(): MutableList<EmailTypeLookup> {


        val contactBx = boxStore.boxFor(EmailTypeLookup::class.java!!)
        return contactBx.all
    }

    fun getAddressLookUp(): MutableList<AddressTypeLookup> {


        val contactBx = boxStore.boxFor(AddressTypeLookup::class.java!!)
        return contactBx.all
    }

    fun getDateLookUp(): MutableList<DateTypeLookup> {


        val contactBx = boxStore.boxFor(DateTypeLookup::class.java!!)
        return contactBx.all
    }

}


}