package com.test.contactapp.data

import android.content.Context
import com.test.contactapp.data.models.CallModel
import com.test.contactapp.data.objbox.Contact
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


}