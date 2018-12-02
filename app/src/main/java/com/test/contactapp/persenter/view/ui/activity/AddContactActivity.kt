package com.test.contactapp.persenter.view.ui.activity

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cdnsol.demo.dsldemo.presenter.viewmodel.AddContactViewModel
import com.test.contactapp.ContactApp
import com.test.contactapp.R
import com.test.contactapp.data.objbox.*
import com.test.contactapp.di.component.ActivityComponent
import com.test.contactapp.di.component.DaggerActivityComponent
import com.test.sampleroom.presenter.viewmodel.GlobalViewModelFactory

import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.call_context_item.*
import kotlinx.android.synthetic.main.content_add_contact.*
import javax.inject.Inject

class AddContactActivity : AppCompatActivity() {

    companion object {
        val TAG = "AddContactActivity"
    }

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder().appComponent((application as ContactApp).component).build()
    }

    @Inject
    lateinit var factory: GlobalViewModelFactory<AddContactViewModel>

    var mainViewModel: AddContactViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        setSupportActionBar(toolbar)
        activityComponent.inject(this)

        mainViewModel = ViewModelProviders.of(this, factory).get(AddContactViewModel::class.java)
        //test.sayHello()
        setObserver()


        fab.setOnClickListener {
           /* val contact =Contact()
            contact.first_name =edittext_name.text.toString()
            contact.last_name =edittext_lastname.text.toString()
            contact.note =edittext_note.text.toString()

            val phone = Phone();
            phone.phone_number=edittext_phonenumber.text.toString()
            phone.phone_type=edittext_phone_type.text.toString()
            contact.phones.add(phone);

            val website = Website();
            website.website=edittext_website.text.toString()
            contact.websites.add(website)

            val address =Address();
            address.address=edittext_address.text.toString()
            address.address_type=edittext_address_type.text.toString()
            contact.addresses.add(address)

            val email = Email();
            email.email_address=edittext_email.text.toString()
            contact.emails.add(email)
            mainViewModel?.saveContact(contact)*/
           // mainViewModel?.getContact()
        }

    }
    private fun setObserver() {
        mainViewModel?.getSampleData()?.observe(this, Observer { data ->
            Log.e(MainActivity.TAG, "data rece ${data}")

        })


    }

}
