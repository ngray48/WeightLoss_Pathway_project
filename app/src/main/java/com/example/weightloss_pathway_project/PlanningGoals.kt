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
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class PlanningGoals : AppCompatActivity() {
    private lateinit var back : Button
    private lateinit var submitPlan : Button
    private lateinit var completeGoal : MultiAutoCompleteTextView
    private lateinit var overComeObstacles : MultiAutoCompleteTextView
    private lateinit var selectedGoal : DefinedGoal
    private var firebaseUser : FirebaseUser? = null
    private lateinit var database: DatabaseReference
    private lateinit var colar : String
    private lateinit var colorDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getColor()
        modifyTheme()
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(R.layout.activity_planning_goals)

            initialize()
            onClick()
        }, 500)
    }

    fun initialize(){
        selectedGoal = getGoal()
        back = findViewById(R.id.plannedBackBtn)
        submitPlan = findViewById(R.id.plannedAddGoalBtn)
        completeGoal = findViewById(R.id.planCompleteGoalViewTxt)
        overComeObstacles = findViewById(R.id.planToOvercomeViewTxt)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        database = Firebase.database.reference.child("users").child(firebaseUser!!.uid).child("plannedGoals")
    }

    private fun getGoal() : DefinedGoal{
        return intent.extras?.get("definedGoal") as DefinedGoal
    }

    private fun backActivity(view: Int){
        val intent = Intent(this, SelectedGoalWeekly::class.java)
        startActivity(intent)
    }

    fun onClick(){
        back.setOnClickListener{
            backActivity(R.layout.activity_selected_goal_weekly)
        }

        submitPlan.setOnClickListener{
            selectedGoal.planToComplete = completeGoal.text.toString()
            selectedGoal.planToOvercome = overComeObstacles.text.toString()

            if(selectedGoal.planToComplete != "" && selectedGoal.planToOvercome != ""){
                val snackbar: Snackbar = Snackbar
                    .make(findViewById(R.id.plannedBackBtn), "Planned Finished?", Snackbar.LENGTH_LONG)
                snackbar.setAction("YES"){
                    writeNewGoal()
                    Toast.makeText(this, "Goal Successfully Planned", Toast.LENGTH_SHORT).show()
                    backActivity(R.layout.activity_selected_goal_weekly)
                }

                snackbar.show()
            }
            else{
                Toast.makeText(this, "Enter Valid Plans for the Goal", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Write new Account to database with username as userId
    private fun writeNewGoal(){
        database.push().setValue(selectedGoal)
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