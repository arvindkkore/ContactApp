package com.test.contactapp.presenter.view.viewmodel



import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.contactapp.data.objbox.*
import com.test.contactapp.di.scope.ActivityScope
import com.test.contactapp.domain.interactor.db.AddContact
import com.test.contactapp.domain.interactor.db.GetContactFromDB
import com.test.contactapp.domain.interactor.db.GetLookupData


import javax.inject.Inject

@ActivityScope
class AddContactViewModel @Inject constructor(val context: Context, val addContact: AddContact,val getContactFromDB: GetContactFromDB,val getLookupData: GetLookupData) : ViewModel()
{

    lateinit var phoneTypeLookup:List<PhoneTypeLookup>
    lateinit var emailTypeLookup:List<EmailTypeLookup>
    lateinit var addressTypeLookup: List<AddressTypeLookup>
    lateinit var dateTypeLookup:List<DateTypeLookup>

  companion object {
      val TAG ="AddContactViewModel"
  }
  object contactLiveData :MutableLiveData<Contact>()
  object sampleLiveData  : MutableLiveData<Boolean>()

  fun getSampleData():MutableLiveData<Boolean> =sampleLiveData

  fun saveContact(contact: Contact)
  {
    addContact.execute(contact).subscribe({data->
                sampleLiveData.value = true},{error->Log.e(TAG,"${error.message}")}
    )
  }
  fun getContact()
  {
   getContactFromDB.execute(1).subscribe({
      Log.e("ABC ",it.first_name)
     Log.e("ABC ","Phone Number count ${it.phones.count()}")

   },{})
  }

    fun getLookupData()
    {
        getLookupData.execute(1).subscribe({lookupdata->
            phoneTypeLookup=lookupdata.phoneLookup
            emailTypeLookup=lookupdata.emaiLookup
            addressTypeLookup=lookupdata.addressLookup
            dateTypeLookup = lookupdata.dateLookup

        },{error-> Log.e(TAG,"Error ${error.message}")})
    }
}
