package com.test.contactapp

import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.content_register.*


class RegisterActivity : AppCompatActivity() {
    companion object {
        val  TAG ="RegisterActivity"
    }
    lateinit var  firebaseAuth:FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolbar)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        button_register.setOnClickListener {
            registerUser(editText_username.text.toString(),editText_password.text.toString())
        }
    }

    fun registerUser(email:String,password:String)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }
    }
    override fun onResume() {
        super.onResume()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.getCurrentUser()
        updateUI(currentUser)

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        firebaseAuth.let {
            Log.e(TAG,currentUser.toString())
            Log.e(TAG,currentUser?.displayName)
        }
    }
}
