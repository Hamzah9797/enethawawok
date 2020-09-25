package com.example.enethawawok.activities.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.enethawawok.R
import com.example.enethawawok.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import util.DATA_USERS

class SignupActivity : AppCompatActivity() {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser
        if(user != null) {
            startActivity(EnethawawokActivity.newIntent(this))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    override fun onStart() {
       super.onStart()
        firebaseAuth.addAuthStateListener { firebaseAuthListener }
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener { firebaseAuthListener }
    }




    fun onSignup(v: View){
        if(!emailEt.text.toString().isNullOrEmpty() && !passwordET.text.toString().isNullOrEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(emailEt.text.toString(), passwordET.text.toString())
                .addOnCompleteListener{ task->
                    if(!task.isSuccessful){
                     Toast.makeText(this,"Signup error ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    } else {
                        val email = emailEt.text.toString()
                        val userId = firebaseAuth.currentUser?.uid?: ""
                        val user = User(userId, "", "", email, "", "", "")
                        firebaseDatabase.child(DATA_USERS).child(userId).setValue(user)
                    }
                }
        }
    }

    companion object{
        fun newIntent(context: Context?) = Intent(context,
            SignupActivity::class.java)
    }
}