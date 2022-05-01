package com.example.weightloss_pathway_project

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Settings : AppCompatActivity() {
    private lateinit var colorChoices : ArrayList<String>
    private lateinit var colorChoice : Spinner
    private lateinit var colorSelection : String
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var submit : Button
    private lateinit var back : Button
    private var firebaseUser : FirebaseUser? = null
    private lateinit var colorDatabase: DatabaseReference
    private lateinit var colar : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        colar = receiveColor()
        modifyTheme()
        setContentView(R.layout.activity_settings)
        title = "Settings"

        initialize()
        setSpinnerItems()
        onClick()

        // getting access to current user
        firebaseUser = FirebaseAuth.getInstance().currentUser
        colorDatabase = Firebase.database.reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("colorTheme")


    }

    private fun initialize(){
        colorChoices = ArrayList(arrayListOf("Red", "Orange", "Yellow", "Green", "Blue","Purple"))
        colorChoice = findViewById(R.id.settingChooseColor)
        auth= FirebaseAuth.getInstance()
        database = Firebase.database.reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("colorTheme")
        submit = findViewById(R.id.settingsSubmitBtn)
        back = findViewById(R.id.settingsBackBtn)

    }

    private fun setSpinnerItems() {
        // Create an ArrayAdapter using the string array and a default spinner layout

        val colorAdapter = ArrayAdapter(
            this,
            R.layout.spinnerlist, colorChoices
        )
        colorChoice.adapter = colorAdapter

        colorChoice.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                colorSelection = colorChoices[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

    }

    fun onClick(){
        submit.setOnClickListener {
            val snackbar: Snackbar = Snackbar
                .make(findViewById(R.id.settingsSubmitBtn), "Are you sure?", Snackbar.LENGTH_LONG)
            snackbar.setAction("YES"){
                database.setValue(colorSelection)
                Toast.makeText(this, "Theme Changed to $colorSelection", Toast.LENGTH_SHORT).show()
                mainActivity(R.layout.activity_main)
            }

            snackbar.show()
        }

        back.setOnClickListener{
            mainActivity(R.layout.activity_main)
        }
    }

    private fun mainActivity(view : Int) {
        val intent = Intent(this, Main::class.java)
        intent.putExtra("color", colar)
        startActivity(intent)
    }

    // Access Intent object from previous activity
    private fun receiveColor(): String {
        return intent.extras?.get("color") as String
    }

//    private fun getColor(){
//        // getting access to current user
//        firebaseUser = FirebaseAuth.getInstance().currentUser
//        colorDatabase = Firebase.database.reference.child("users").child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).child("colorTheme")
//
//        colar = ""
//
//        val postListener2 = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Get Post object and use the values to update the UI
//
//                colar = dataSnapshot.getValue<String>()!!
//                Log.e("Color", colar)
//                modifyTheme()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        colorDatabase.addValueEventListener((postListener2))
//
//        Log.e("Color2", colar)
//    }

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