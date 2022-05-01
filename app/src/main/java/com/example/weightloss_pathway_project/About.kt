package com.example.weightloss_pathway_project

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class About : AppCompatActivity() {
    private lateinit var colar : String
    private lateinit var colorDatabase: DatabaseReference
    private var firebaseUser : FirebaseUser? = null
    private lateinit var menu : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getColor()
        modifyTheme()
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(R.layout.activity_about)
            initialize()
            onClick()
            title = "About Us!"
        }, 1000)
    }

    private fun initialize(){
        menu = findViewById(R.id.aboutMenuBtn)
    }

    private fun onClick(){
        // create action when menu button pressed
        menu.setOnClickListener{
            mainActivity(R.layout.activity_main)
        }
    }

    // Intent that will open main activity when activated
    private fun mainActivity(view: Int){
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
    }

    fun getColor(){
        // getting access to current user
        firebaseUser = FirebaseAuth.getInstance().currentUser
        colorDatabase = Firebase.database.reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("colorTheme")

        colar = ""

        val postListener2 = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                colar = dataSnapshot.getValue<String>()!!
                modifyTheme()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        colorDatabase.addValueEventListener((postListener2))
    }

    private fun modifyTheme(){
        val window = this.window
        val col = ColorChange()
        val c = col.defineThemeColor(colar)
        val color = ColorDrawable(Color.parseColor(c))

        if (colar == "Red"){
            setTheme(R.style.redTheme)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.red)
            supportActionBar?.setBackgroundDrawable(color)
        }
        else if (colar == "Orange"){
            setTheme(R.style.orangeTheme)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.orange)
            supportActionBar?.setBackgroundDrawable(color)
        }
        else if (colar == "Yellow"){
            setTheme(R.style.yellowTheme)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.yellow)
            supportActionBar?.setBackgroundDrawable(color)
        }
        else if (colar == "Green"){
            setTheme(R.style.greenTheme)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.green)
            supportActionBar?.setBackgroundDrawable(color)
        }
        else if (colar == "Blue"){
            setTheme(R.style.blueTheme)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue)
            supportActionBar?.setBackgroundDrawable(color)
        }
        else if (colar == "Purple"){
            setTheme(R.style.purpleTheme)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.purple)
            supportActionBar?.setBackgroundDrawable(color)
        }
    }

}