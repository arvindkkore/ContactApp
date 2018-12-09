package com.test.contactapp.domain.repository

import com.test.contactapp.data.models.CallModel
import com.test.contactapp.data.models.LookupData
import com.test.contactapp.data.objbox.Contact
import io.reactivex.Single


public interface Repository
{
  public  fun readCallLog(): Single<MutableList<CallModel>>
    fun getAllCotactsDB(): Single<MutableList<Contact>>
  fun getCotactDB(contactid: Long?): Single<Contact>
  fun addContact(contact: Contact?): Single<Long>
  fun addContacts(contacts: MutableList<Contact>): Single<Boolean>
    fun prepareLookData(): Single<Boolean>
  fun getLookupData(): Single<LookupData>
  fun readContacts(): Single<MutableList<Contact>>
}
