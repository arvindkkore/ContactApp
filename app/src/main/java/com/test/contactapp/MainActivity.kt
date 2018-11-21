package com.test.contactapp

import android.content.Intent

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    companion object {
        val  TAG = "MainActivity"
    }
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance();
        Handler().postDelayed( {
            val currentUser = auth.currentUser
            Log.e(TAG,"Current User ${auth.currentUser?.email}")
            if (currentUser == null)
            {
                Log.e(TAG,"Login Page")
                val  intent = Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent)
            }else
            {
                Log.e(TAG,"Home Page")
                val intent =Intent(this@MainActivity,HomeActivity::class.java);
                startActivity(intent)

            }
            finish()
        },5000)




    }
}
