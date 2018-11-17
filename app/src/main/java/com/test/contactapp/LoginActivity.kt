package com.test.contactapp

import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        val TAG ="LoginActivity"
    }
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        auth =FirebaseAuth.getInstance();

        button2.setOnClickListener {
        login(email = editText.text.toString(),password = editText2.text.toString())
        }
    }

  fun login(email:String ,password:String)
  {
      auth.signInWithEmailAndPassword(email, password)
          .addOnCompleteListener(this) { task ->
              if (task.isSuccessful) {
                  // Sign in success, update UI with the signed-in user's information
                  Log.d(TAG, "signInWithEmail:success")
                  val user = auth.currentUser
                  Log.e(TAG,user?.email)
                  //updateUI(user)
              } else {
                  // If sign in fails, display a message to the user.
                  Log.w(TAG, "signInWithEmail:failure", task.exception)
                  Toast.makeText(baseContext, "Authentication failed.",
                      Toast.LENGTH_SHORT).show()
                  //updateUI(null)
              }

              // ...
          }

  }
}
