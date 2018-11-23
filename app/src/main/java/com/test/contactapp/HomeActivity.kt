package com.test.contactapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar


import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import kotlinx.android.synthetic.main.activity_home.*

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.test.contactapp.R.id.toolbar
import kotlinx.android.synthetic.main.header_layout.*
import kotlin.math.log
import android.content.Context.SEARCH_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView


class HomeActivity : AppCompatActivity() {

    lateinit var imageView: ImageView

    companion object {
        val TAG = "HomeActivity"
    }
    lateinit var auth:FirebaseAuth
    private var mDrawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        auth = FirebaseAuth.getInstance();
        val navigation = findViewById(R.id.bottom_navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        loadFragment(HomeFragment())
        toolbar.title = "Home 1"
        val user =auth.currentUser
        Log.e(TAG," user detail ${user?.email}")
        Glide.with(this).load(user?.photoUrl).apply(RequestOptions().circleCrop()).into(nav_view.getHeaderView(0).findViewById(R.id.profile_image))
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.user_name).text =user?.displayName

        mDrawerLayout = findViewById(R.id.drawer_layout)

        nav_view.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout?.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)

        }


    }



    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
            val fragment: Fragment
            when (item.getItemId()) {
                R.id.home -> {
                     toolbar.setTitle("Home 1")
                    fragment = HomeFragment()
                    loadFragment(fragment)
                    return true
                }
                R.id.home1 -> {
                     toolbar.setTitle("Home 2")
                    fragment = ContactFragmentNew()
                    loadFragment(fragment)
                    return true
                }
                R.id.home2 -> {
                     toolbar.setTitle("Home 3")
                    fragment = CallFragmentNew()
                    loadFragment(fragment)
                    return true
                }

            }

            return false
        }
    }

    fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)

        val searchManager = this@HomeActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem!!.getActionView() as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(this@HomeActivity.getComponentName()))
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout?.openDrawer(GravityCompat.START)
                true
            }
            R.id.call_log -> {
                val intent =Intent(this@HomeActivity,ContactDetailActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

