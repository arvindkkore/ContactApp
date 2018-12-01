package com.test.contactapp.persenter.view.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.test.contactapp.R
import com.test.contactapp.persenter.view.ui.fragments.AboutFragment
import com.test.contactapp.persenter.view.ui.fragments.LinkFragment
import com.test.contactapp.persenter.view.ui.fragments.NoteFragment

import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.content_contact_detail.*
import android.widget.Toast
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

//https://guides.codepath.com/android/viewpager-with-fragmentpageradapter
class ContactDetailActivity : AppCompatActivity() {

    private lateinit var adapterViewPager: MyPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        adapterViewPager = MyPagerAdapter(
            this@ContactDetailActivity,
            supportFragmentManager
        );
        container.setAdapter(adapterViewPager);


        // Attach the page change listener inside the activity
        container.addOnPageChangeListener(object : OnPageChangeListener {

            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {
                Toast.makeText(
                    this@ContactDetailActivity,
                    "Selected page position: $position", Toast.LENGTH_SHORT
                ).show()
            }

            // This method will be invoked when the current page is scrolled
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            override fun onPageScrollStateChanged(state: Int) {
                // Code goes here
            }
        })
     tablayout.setupWithViewPager(container)

    }

// Attach the page change listener inside the activity



 class MyPagerAdapter(val mContext:Context,fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


        // Returns total number of pages
        override fun getCount() = 3

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                  return  AboutFragment.newInstance("String1","Sting 2")
                }
                1 -> {
                  return  LinkFragment.newInstance("String1","Sting 2")
                }
                2 -> {
                  return  NoteFragment.newInstance("String1","Sting 2")
                }
                else -> {
                  return  AboutFragment.newInstance("String1","Sting 2")
                }
            }
        }


        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int) = when (position){
            0-> mContext.resources.getString(R.string.tab_about)
            1 ->  mContext.resources.getString(R.string.tab_links)
            2 ->  mContext.resources.getString(R.string.tab_note)
            else ->  mContext.resources.getString(R.string.tab_about)
        }

    }

}
