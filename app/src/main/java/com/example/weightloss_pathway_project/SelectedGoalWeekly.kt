package com.example.weightloss_pathway_project

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
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
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class SelectedGoalWeekly : AppCompatActivity() {
    private lateinit var coachesGoals : ListView
    private lateinit var goalDate : TextView
    private lateinit var goalButton : Button
    private lateinit var personalButton : Button
    private lateinit var viewCoachGoals : Button
    private lateinit var back : Button
    private lateinit var dateString : String
    private lateinit var goalList : ArrayList<String>
    private lateinit var clientDatabase: DatabaseReference
    private lateinit var coachDatabase: DatabaseReference
    private var currentNutritionalGoals: ArrayList<NutritionalGoals>? = null
    private var firebaseUser : FirebaseUser? = null
    private var currentFitnessGoals: ArrayList<FitnessGoals>? = null
    private var coachGoals: ArrayList<DefinedGoal>? = null
    private var clientGoals: ArrayList<DefinedGoal>? = null
    private lateinit var weekDay : String
    private lateinit var colar : String
    private lateinit var colorDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getColor()
        modifyTheme()
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(R.layout.activity_selected_goal_weekly)
            initialize()
            onClick()
            gettingGoals()
            createDateGoal()
        }, 500)
    }

    // Initialize all variables
    private fun initialize(){
        coachesGoals = findViewById(R.id.coachCreatedGoalsLstVw)
        goalDate = findViewById(R.id.selectedDateTxt)
        goalDate.text = getCurrentDay()
        goalButton = findViewById(R.id.selectedDateBtn)
        personalButton = findViewById(R.id.weeklyPersonalGoalBtn)
        viewCoachGoals = findViewById(R.id.newCoachGoalsBtn)
        back = findViewById(R.id.weeklyPersonalBackBtn)
        dateString = String()
        goalList = ArrayList()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        clientDatabase = Firebase.database.reference.child("users").child(firebaseUser!!.uid).child("clientGoals")
        coachDatabase = Firebase.database.reference.child("users").child(firebaseUser!!.uid).child("coachGoals")
        currentFitnessGoals = ArrayList()
        currentNutritionalGoals = ArrayList()
        coachGoals = ArrayList()
        clientGoals = ArrayList()
    }

    // Get goals from the database
    // Query occurs asynchronously and requires downhill listview setting within function
    private fun gettingGoals(){
        // Access Database

        val postListenerCoach = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for (dataSnapShot in dataSnapshot.children) {
                    val newCoa = dataSnapShot.getValue<DefinedGoal>()!!
                    coachGoals?.add(newCoa)
                }
                //createNutritionalGoals()
                createCoachGoal()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        coachDatabase.addValueEventListener(postListenerCoach)

        val postListenerClient = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for (dataSnapShot in dataSnapshot.children) {
                    val newCoa = dataSnapShot.getValue<DefinedGoal>()!!
                    clientGoals?.add(newCoa)
                }
                //createNutritionalGoals()
                createClientGoal()
                setListView()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        clientDatabase.addValueEventListener(postListenerClient)
    }

    // creates listview occurrence for date
    private fun createDateGoal(){
        if (dateString != String()) {
            goalList.add(dateString)
        }
    }

  //  creates coach goals and adds to list for listview occurrence
    fun createCoachGoal() {
        if (coachGoals != null) {
            for (item: DefinedGoal in coachGoals!!) {
                if(item.date == goalDate.text.toString()){
                    goalList.add(item.goal)
                }
            }
        }
    }

    //  creates client goals and adds to list for listview occurrence
    fun createClientGoal() {
        if (clientGoals != null) {
            for (item: DefinedGoal in clientGoals!!) {
                if(item.date == goalDate.text.toString()){
                    goalList.add(item.goal)
                }
            }
        }
    }

    // sets listview values
    fun setListView(){
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, goalList
        )

        coachesGoals.adapter = arrayAdapter
        coachesGoals.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val coachesGoal = DefinedGoal()
                val goal = coachesGoals.getItemAtPosition(position) as String
                coachesGoal.goal = goal
                coachesGoal.date = goalDate.text.toString()
                coachesGoal.dow = weekDay

                planActivity(R.layout.activity_planning_goals, coachesGoal)
            }
    }

    // onClick methods with component clicks
    private fun onClick(){
        val dOW = DayOfWeek()
        val dates = Date()
        goalButton.setOnClickListener {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)

            val myDatePicker =
                DatePickerDialog(
                    this,
                    android.R.style.ThemeOverlay,
                    { _, Year, Month, Day ->
                        dOW.dd = Day
                        dOW.mm = Month + 1
                        dOW.yyyy = Year
                        val d = "${dates.monthToString(Month + 1)} $Day, $Year, ${dOW.calculate()}"
                        goalDate.text = d
                        //dayOfWeek = DOW.calculate()
                    },
                    year,
                    month,
                    day
                )
            myDatePicker.show()
        }

        viewCoachGoals.setOnClickListener{
            coachGoals?.clear()
            clientGoals?.clear()
            goalList.clear()

            gettingGoals()
            createDateGoal()
        }

        personalButton.setOnClickListener{
            personalGoalActivity(R.layout.activity_created_goal_weekly)
        }

        back.setOnClickListener{
            mainActivity(R.layout.activity_main)
        }
    }

    // Get current date and day
    private fun getCurrentDay() : String{
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]
        Log.e("Day", "$day")

        val date = LocalDate.now()
        val d = Date()
        val dateSplit = date.toString().split(("-"))
        val month = d.monthToString(dateSplit[1].toInt())
        val dae = dateSplit[2].toInt().toString()
        val year = dateSplit[0].toInt().toString()
        weekDay = d.dayToString(day - 1)

        return "$month $dae, $year, $weekDay"
    }

    // Intent that will open PlanningGoals activity when activated
    private fun planActivity(view: Int, coachesGoal : DefinedGoal){
        val intent = Intent(this, PlanningGoals::class.java)
        intent.putExtra("definedGoal", coachesGoal)
        startActivity(intent)
    }

    // Intent that will open main activity when activated
    private fun mainActivity(view: Int){
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
    }

    // Intent that will open main activity when activated
    private fun personalGoalActivity(view: Int){
        val intent = Intent(this, CreatedGoalWeekly::class.java)
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