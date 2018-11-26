package com.test.contactapp.persenter.view.ui.activity

import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn


import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.test.contactapp.R


class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 123

    var mAuthListner: FirebaseAuth.AuthStateListener? = null
    private lateinit var googleSignInClient: GoogleSignInClient
    companion object {
        val TAG ="LoginActivity"
    }
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        auth =FirebaseAuth.getInstance();

        google_btn.setOnClickListener { signIn() }
        button2.setOnClickListener {
        login(email = editText.text.toString(),password = editText2.text.toString())
        }
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
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

    // [START onactivityresult]
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                //updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]
    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        //showProgressDialog()
        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

          //          updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    //Snackbar.make(getro, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    Toast.makeText(this@LoginActivity,"Authentication Failed",Toast.LENGTH_LONG).show()
                  //  updateUI(null)
                }
                // [START_EXCLUDE]
              //  hideProgressDialog()
                // [END_EXCLUDE]
            }
    }
    // [END auth_with_google]
    // [START signin]
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]
    private fun signOut() {
        // Firebase sign out
        auth.signOut()
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            //updateUI(null)
        }
    }
    private fun revokeAccess() {
        // Firebase sign out
        auth.signOut()
        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
            //updateUI(null)
        }
    }
}
