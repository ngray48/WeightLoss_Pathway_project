package com.example.weightloss_pathway_project

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
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


class WeeklyTab : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var dateSelection: TextView
    private var firebaseUser : FirebaseUser? = null
    private lateinit var submit : Button
    private lateinit var date: Button
    private lateinit var back : Button
    private var colar = ""
    private lateinit var colorDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "WeightLoss Goals"
        getColor()
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(R.layout.activity_weekly_tab)
            initialize()
            initializeFragment()
            onClick()
            if (dateSelection.text.toString() == "") {
                dateSelection.text = getCurrentDay()
                Handler(Looper.getMainLooper()).postDelayed({
                    setTabs()
                }, 200)
            }
        }, 300)

    }

    private fun initialize() {
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("Sun"))
        tabLayout.addTab(tabLayout.newTab().setText("Mon"))
        tabLayout.addTab(tabLayout.newTab().setText("Tue"))
        tabLayout.addTab(tabLayout.newTab().setText("Wed"))
        tabLayout.addTab(tabLayout.newTab().setText("Thu"))
        tabLayout.addTab(tabLayout.newTab().setText("Fri"))
        tabLayout.addTab(tabLayout.newTab().setText("Sat"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        dateSelection = findViewById(R.id.weeklyTabDateTxt)
        date = findViewById(R.id.weeklyTabDateBtn)
        submit = findViewById(R.id.weeklyTabDateSubmitBtn)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        back = findViewById(R.id.weeklyTabBackBtn)
    }

    private fun initializeFragment() {
        val adapter = MyTabAdapter(
            this, supportFragmentManager,
            tabLayout.tabCount
        )
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 7
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun onClick() {
        val dOW = DayOfWeek()
        val dates = Date()
        date.setOnClickListener {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)

            val myDatePicker =
                DatePickerDialog(
                    this,
                    android.R.style.ThemeOverlay,
                    { _, Year, Month, Day  ->
                        dOW.dd = Day
                        dOW.mm = Month + 1
                        dOW.yyyy = Year
                        val d = "${dates.monthToString(Month + 1)} $Day, $Year, ${dOW.calculate()}"
                        dateSelection.text = d
                    },
                    year,
                    month,
                    day
                )
            myDatePicker.show()
        }

        submit.setOnClickListener{
            setTabs()
        }

        back.setOnClickListener {
            mainActivity(R.layout.activity_main)
        }

    }

    private fun selectPage(pageIndex: Int) {
        tabLayout.setScrollPosition(pageIndex, 0f, true)
        viewPager.currentItem = pageIndex
    }

    private fun getDay() : String{
        val splitDate = dateSelection.text.toString().replace(",", "").split(" ")
        return splitDate[3]
    }

    private fun getCurrentDay() : String{
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]

        val date = LocalDate.now()
        val d = Date()
        val dateSplit = date.toString().split(("-"))
        val month = d.monthToString(dateSplit[1].toInt())
        val dae = dateSplit[2].toInt().toString()
        val year = dateSplit[0].toInt().toString()
        val weekDay = d.dayToString(day - 1)

        return "$month $dae, $year, $weekDay"
    }

    private fun setTabs(){
        val da = DayOfWeek()
        val sunday = da.findDayOfWeek(dateSelection.text.toString())

        val sun = Sunday()
        val bunSun = Bundle()
        bunSun.putString("date", sunday)
        bunSun.putString("color", colar)
        sun.arguments = bunSun
        supportFragmentManager.beginTransaction().replace(R.id.fragSunday, sun).commit()

        val mon = Monday()
        val bunMon = Bundle()
        val monday = da.nextDay(sunday)
        bunMon.putString("date", monday)
        bunMon.putString("tabColor", colar)
        mon.arguments = bunMon
        supportFragmentManager.beginTransaction().replace(R.id.fragMonday, mon).commit()

        val tue = Tuesday()
        val bunTue = Bundle()
        val tuesday = da.nextDay(monday)
        bunTue.putString("date", tuesday)
        bunTue.putString("tabColor", colar)
        tue.arguments = bunTue
        supportFragmentManager.beginTransaction().replace(R.id.fragTuesday, tue).commit()

        val wed = Wednesday()
        val bunWed = Bundle()
        val wednesday = da.nextDay(tuesday)
        bunWed.putString("date", wednesday)
        bunWed.putString("tabColor", colar)
        wed.arguments = bunWed
        supportFragmentManager.beginTransaction().replace(R.id.fragWednesday, wed).commit()

        val thu = Thursday()
        val bunThu = Bundle()
        val thursday = da.nextDay(wednesday)
        bunThu.putString("date", thursday)
        bunThu.putString("tabColor", colar)
        thu.arguments = bunThu
        supportFragmentManager.beginTransaction().replace(R.id.fragThursday, thu).commit()

        val fri = Friday()
        val bunFri = Bundle()
        val friday = da.nextDay(thursday)
        bunFri.putString("date", friday)
        bunFri.putString("tabColor", colar)
        fri.arguments = bunFri
        supportFragmentManager.beginTransaction().replace(R.id.fragFriday, fri).commit()

        val sat = Saturday()
        val bunSat = Bundle()
        val saturday = da.nextDay(friday)
        bunSat.putString("date", saturday)
        bunSat.putString("tabColor", colar)
        sat.arguments = bunSat
        supportFragmentManager.beginTransaction().replace(R.id.fragSaturday, sat).commit()

        val d = Date()
        val index = d.dayToNumber(getDay())
        selectPage(index)
    }

    private fun mainActivity(view : Int) {
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
    }

    fun getColor(){
        // getting access to current user
        firebaseUser = FirebaseAuth.getInstance().currentUser
        colorDatabase = Firebase.database.reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("colorTheme")

        val postListener2 = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                colar = dataSnapshot.getValue<String>()!!
                Log.e("Color", colar)
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
