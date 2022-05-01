package com.example.weightloss_pathway_project

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.MultiAutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
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

class PlannedView : AppCompatActivity() {
    private lateinit var planToComplete : MultiAutoCompleteTextView
    private lateinit var planToOvercome : MultiAutoCompleteTextView
    private lateinit var back : Button
    private lateinit var viewGoal : DefinedGoal
    private lateinit var colar : String
    private var firebaseUser : FirebaseUser? = null
    private lateinit var colorDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getColor()
        modifyTheme()
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(R.layout.activity_planned_view)

            initialize()
            onClick()
            try{
                getPlans()
            }
            catch (e : Exception){
            }

            setValues()
        }, 500)
    }

    fun initialize(){
        planToComplete = findViewById(R.id.planCompleteGoalViewTxt)
        planToOvercome = findViewById(R.id.planToOvercomeViewTxt)
        back = findViewById(R.id.plannedBackViewBtn)
        //viewGoal = DefinedGoal()
        viewGoal = getPlans()
    }

    private fun getPlans() : DefinedGoal{
        return intent.extras?.get("viewGoal") as DefinedGoal
    }

    private fun setValues(){
        planToComplete.setText(viewGoal.planToComplete)
        planToOvercome.setText(viewGoal.planToOvercome)
    }

    fun onClick(){
        back.setOnClickListener{
            weeklyTabActivity(R.layout.activity_weekly_tab)
        }
    }

    // Intent that will open main activity when activated
    private fun weeklyTabActivity(view: Int){
        val intent = Intent(this, WeeklyTab::class.java)
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

    fun modifyTheme(){
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