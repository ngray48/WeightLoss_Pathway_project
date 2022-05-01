package com.example.weightloss_pathway_project

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.util.*

class CreateUserName : AppCompatActivity() {
    private var creatingUser : Client = Client()
    private lateinit var firstname : TextView
    private lateinit var lastname : TextView
    private lateinit var dateSelection: TextView
    private lateinit var dateBtn: Button
    private lateinit var next: Button
    private lateinit var cancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_name)
        title = "Account Creation"

        instantiate()
        onClick()
    }

    // Intent that will open contact info activity when activated
    private fun contactInfoActivity(view: Int){
        val intent = Intent(this, CreateUserContactInfo::class.java)
        intent.putExtra("user", creatingUser)
        startActivity(intent)
    }

    // Intent that will open login activity when activated
    private fun loginActivity(view: Int){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    // Instantiates object from XML
    private fun instantiate(){
        firstname = findViewById(R.id.createUserFirstNameTxt)
        lastname = findViewById(R.id.createUserLastNameTxt)
        dateSelection = findViewById(R.id.createUserBirthTxt)
        dateBtn = findViewById(R.id.createUserBirthBtn)
        next = findViewById(R.id.createUserNameNextBtn)
        cancel = findViewById(R.id.createUserCancelBtn)
    }

    // Will handle onClick functionality
    private fun onClick() {
        // create action when next button pressed
        next.setOnClickListener {
            if (firstname.text.isEmpty()) {
                Snackbar.make(
                    findViewById(R.id.createUserFirstNameTxt),
                    "Fill in First Name",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (lastname.text.isEmpty()) {
                Snackbar.make(
                    findViewById(R.id.createUserFirstNameTxt),
                    "Fill in Last Name",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (dateSelection.text.isEmpty()) {
                Snackbar.make(
                    findViewById(R.id.createUserFirstNameTxt),
                    "Fill in Birthdate",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                // Valid and assign first name
                val validFirstName = creatingUser.checkName(firstname.text.toString().trim())
                creatingUser.firstname = validFirstName

                // Valid and assign last name
                val validLastName = creatingUser.checkName(lastname.text.toString().trim())
                creatingUser.lastname = validLastName
                // Valid and assign date
                creatingUser.birthday = dateSelection.text.toString().trim()

                val snackbar: Snackbar = Snackbar
                    .make(findViewById(R.id.createUserNameNextBtn), "Is your information correct?", Snackbar.LENGTH_LONG)
                snackbar.setAction("YES"){
                    contactInfoActivity(R.layout.activity_create_user_contact_info)
                }

                snackbar.show()
            }

        }

        // create action when cancel button pressed
        cancel.setOnClickListener {
            loginActivity(R.layout.activity_login)
        }

        // Access Calendar to select and format birthdate
        val dOW = DayOfWeek()
        val dates = Date()
        dateBtn.setOnClickListener {
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
                        val d = "${dates.monthToString(Month + 1)} $Day, $Year"
                        dateSelection.text = d
                    },
                    year,
                    month,
                    day
                )
            myDatePicker.show()
        }
    }
}