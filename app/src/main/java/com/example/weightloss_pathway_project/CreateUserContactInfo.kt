package com.example.weightloss_pathway_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import android.widget.AdapterView
import android.widget.ArrayAdapter


class CreateUserContactInfo : AppCompatActivity() {
    private lateinit var creatingUser : Client
    private lateinit var states : MutableList<String>
    private var stateSelection : String = String()
    private lateinit var street : TextView
    private lateinit var city : TextView
    private lateinit var state : Spinner
    private lateinit var phone : TextView
    private lateinit var zip : TextView
    private lateinit var next : Button
    private lateinit var cancel : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_contact_info)
        title = "Account Creation"

        instantiate()
        createAdapter()
        onClick()
    }

    // Intent that will open credentials activity when activated
    private fun credentialsActivity(view: Int){
        val intent = Intent(this, CreateUserCredentials::class.java)
        intent.putExtra("user", creatingUser)
        startActivity(intent)
    }

    // Intent that will open login activity when activated
    private fun loginActivity(view: Int){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    // Get access to passed intent
    private fun getAccessToCurrentUser(): Client {

        return intent.extras?.get("user") as Client

    }

    // Instantiating objects on XML, list, and user
    private fun instantiate(){
        // Access previous information from first name, last name, and birthdate
        creatingUser = getAccessToCurrentUser()

        states = mutableListOf(
            "State", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID",
            "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
            "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK",
            "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
            "WI", "WY"
        )

        street = findViewById<EditText>(R.id.createContactStreetTxt)
        city = findViewById<EditText>(R.id.createContactCityTxt)
        state = findViewById(R.id.createContactState)
        phone = findViewById<EditText>(R.id.createContactPhoneTxt)
        zip = findViewById<EditText>(R.id.createContactZipTxt)
        next = findViewById(R.id.createUserContactNextBtn)
        cancel = findViewById(R.id.createUserCancelBtn)
    }

    // Creating adapter
    private fun createAdapter(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this,
            R.layout.spinnerlist, states)
        state.adapter = adapter

        state.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                stateSelection = states[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    // Handle onClick functionality
    private fun onClick(){
        // create action when next button pressed
        next.setOnClickListener{

            if (street.text.isEmpty()){
                Snackbar.make(findViewById(R.id.createContactStreetTxt), "Enter Street", Snackbar.LENGTH_LONG).show()
            }
            else if (city.text.isEmpty()){
                Snackbar.make(findViewById(R.id.createContactCityTxt), "Enter City", Snackbar.LENGTH_LONG).show()
            }
            else if (stateSelection == "" || stateSelection == "State"){
                Snackbar.make(findViewById(R.id.createContactState), "Enter State", Snackbar.LENGTH_LONG).show()
            }
            else if (zip.text.isEmpty()){
                Snackbar.make(findViewById(R.id.createContactPhoneTxt), "Enter Zip Code", Snackbar.LENGTH_LONG).show()
            }
            else if (phone.text.isEmpty() || phone.text.length != 10){
                Snackbar.make(findViewById(R.id.createContactPhoneTxt), "Enter Phone Number", Snackbar.LENGTH_LONG).show()
                val number = Phone()
                number.isValidPhone(phone.text.toString())
                if (!number.isNumber){
                    Snackbar.make(findViewById(R.id.createContactPhoneTxt), "Enter Valid Phone Number", Snackbar.LENGTH_LONG).show()
                }
            }
            else{
                val address = Address()
                val number = Phone()

                address.street = street.text.toString().trim()
                address.city = city.text.toString().trim()
                address.state = stateSelection
                address.zip = zip.text.toString().trim()
                val location = address.addressToDatabaseFire().trim()
                creatingUser.address = location


                number.phoneBreakDown(phone.text.toString().trim())
                number.phoneNumber = number.phoneToDatabaseFire().trim()

                creatingUser.phone = number.phoneNumber

                val snackbar: Snackbar = Snackbar
                    .make(findViewById(R.id.createContactStreetTxt), "Is your information correct?", Snackbar.LENGTH_LONG)
                snackbar.setAction("YES" ){
                    credentialsActivity(R.layout.activity_create_user_credentials)
                }

                snackbar.show()
            }
        }

        // create action when cancel button pressed
        cancel.setOnClickListener{
            loginActivity(R.layout.activity_login)
        }
    }
}