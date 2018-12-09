package com.test.contactapp.data

import android.content.Context
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.data.models.LookupData
import com.test.contactapp.data.objbox.*
import com.test.contactapp.data.util.fetchData
import com.test.contactapp.domain.repository.Repository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class RepositoryImpl @Inject constructor(val context: Context, val objectBoxDao: ObjectBoxDao) : Repository {


    override fun readCallLog(): Single<MutableList<CallModel>> {
        return Single.create<MutableList<CallModel>> { emitter ->
            emitter.onSuccess(fetchData(context))
        }
    }

    override fun getAllCotactsDB(): Single<MutableList<Contact>> {
        return Single.create<MutableList<Contact>> { emitter ->
            val contacts = objectBoxDao.getAllContacts()
            emitter.onSuccess(contacts)
        }
    }

    override fun getCotactDB(contactid: Long?): Single<Contact> {
        return Single.create<Contact> { emitter ->
            val contacts = objectBoxDao.getContact(contactid)
            emitter.onSuccess(contacts)
        }
    }

    override fun addContact(contact: Contact?): Single<Long> {
        return Single.create<Long> { emitter ->
            if (contact != null) {
                val contactId = objectBoxDao.insertContact(contact)
                emitter.onSuccess(contactId)
            } else {

                emitter.onSuccess(0)
            }


        }
    }

    override fun addContacts(contacts: MutableList<Contact>): Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            val contactId = objectBoxDao.insertContacts(contacts)
            emitter.onSuccess(contactId)
        }
    }
    override fun  prepareLookData():Single<Boolean>
    {
        return Single.create<Boolean> { emitter ->
            //look up PhoneType
            //Mobile
            //Work
            //Home
            //Main
            //Work Fax
            //Home Fax
            //Pager
            // Other
            //Custom
           val phones = mutableListOf<PhoneTypeLookup>()
            phones.add(PhoneTypeLookup(0,"Mobile"))
            phones.add(PhoneTypeLookup(0,"Work"))
            phones.add(PhoneTypeLookup(0,"Home"))
            phones.add(PhoneTypeLookup(0,"Main"))
            phones.add(PhoneTypeLookup(0,"Work Fax"))
            phones.add(PhoneTypeLookup(0,"Home Fax"))
            phones.add(PhoneTypeLookup(0,"Pager"))
            phones.add(PhoneTypeLookup(0,"Other"))
            phones.add(PhoneTypeLookup(0,"Custom"))


            objectBoxDao.insertPhoneLookUp(phones)

            //look up EmailType
            val emails = mutableListOf<EmailTypeLookup>()
            emails.add(EmailTypeLookup(0,"Work"))
            emails.add(EmailTypeLookup(0,"Home"))
            emails.add(EmailTypeLookup(0,"Other"))
            emails.add(EmailTypeLookup(0,"Custom"))
            objectBoxDao.insertEmailLookup(emails)


            //look up AddressType

            val addresses = mutableListOf<AddressTypeLookup>()
            addresses.add(AddressTypeLookup(id=0,addressType= "Work"))
            addresses.add(AddressTypeLookup(0,"Home"))
            addresses.add(AddressTypeLookup(0,"Other"))
            addresses.add(AddressTypeLookup(0,"Custom"))
            objectBoxDao.insertAddressLookup(addresses)

            //look up Date  Type

            val dates = mutableListOf<DateTypeLookup>()
            dates.add(DateTypeLookup(id=0,dateType= "BirthDay"))
            dates.add(DateTypeLookup(0,"Anniversary"))
            dates.add(DateTypeLookup(0,"Other"))
            dates.add(DateTypeLookup(0,"Custom"))
            objectBoxDao.insertDateLookup(dates)
            emitter.onSuccess(true)
        }
    }
    override fun  getLookupData() :Single<LookupData>
    {

        //val phones = mutableListOf<PhoneTypeLookup>()
        return Single.create<LookupData> { emitter ->
            val phoneLookup = objectBoxDao.getPhoneLookUp()
            val emailTypeLookup = objectBoxDao.getEmailLookUp()
            val addressTypeLookup = objectBoxDao.getAddressLookUp()
            val dateTypeLookup = objectBoxDao.getDateLookUp()
            val lookupData = LookupData(phoneLookup,emailTypeLookup
            , addressTypeLookup, dateTypeLookup)
            emitter.onSuccess(lookupData)
        }

    }
    override fun readContacts(): Single<MutableList<Contact>> {
     return Single.create<MutableList<Contact>>{emmiter->
          emmiter.onSuccess(com.test.contactapp.data.util.readContacts(context))
     }
    }
}