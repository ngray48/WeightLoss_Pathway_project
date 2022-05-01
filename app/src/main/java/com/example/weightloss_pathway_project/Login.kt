package com.example.weightloss_pathway_project

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*


class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var signIn: Button
    private lateinit var createAccount: Button
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var help: Button
    private lateinit var header: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Set title of page to Login
        title = "Login"

        // Access Authentication
        auth = FirebaseAuth.getInstance()

        initialize()

        if (auth.currentUser != null){
            auth.signOut()
        }

        // OnClick functionality to login
        onClick()
    }

    // Custom details for logging in to firebase
    private fun loggingInUser(view : Int) {
        // Change input to strings for firebase function collaboration

        val emails = email.text.toString()
        val passwords = password.text.toString()

        // Input validation
        if (!TextUtils.isEmpty(emails) && !TextUtils.isEmpty(passwords)) {
            auth.signInWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener(this@Login) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                            email.text = ""
                        password.text = ""
                        Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show()
                        loggedInToMain(R.layout.activity_main)
                    } else {
                        // If sign in fails, display a message to the user.
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthInvalidUserException) {
                            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        } catch (e: FirebaseNetworkException) {
                            Toast.makeText(
                                this,
                                "Connect to Network",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch(e: Exception){
                            Toast.makeText(
                                this@Login, e.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }


    }

    private fun initialize(){
        // link sign in button
        signIn = findViewById(R.id.loginSignInBtn)
        // link create account button
        createAccount = findViewById(R.id.loginCreateAccountBtn)
        // link help button
        help = findViewById(R.id.loginHelpBtn)
        // Get email input of user
        email = findViewById(R.id.loginUserTxt)
        // Get password input of user
        password = findViewById(R.id.loginPasswordTxt)

        header = findViewById(R.id.loginTitleLbl)
        header.setTextColor(getColor(R.color.yellow))
    }

    // Will handle onClick functionality
    private fun onClick(){
        // create action when finish button pressed
        signIn.setOnClickListener{
            loggingInUser(R.layout.activity_login)
        }

        // create action when createAccount button pressed
        createAccount.setOnClickListener{
            createNameActivity(R.layout.activity_create_user_name)
        }

        help.setOnClickListener{
            alertDialog()
        }
    }

    // Intent that will open create user activity when activated
    private fun createNameActivity(view: Int){
        val intent = Intent(this, CreateUserName::class.java)
            startActivity(intent)

    }

    private fun loggedInToMain(view : Int) {
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
    }

    private fun alertDialog() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setMessage("Requires account creation for sign in")
        dialog.setTitle("Sign In Help")
        dialog.setPositiveButton("Okay",
            DialogInterface.OnClickListener { _, _ ->
            })
        dialog.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { _, _ ->
            })
        val alertDialog: AlertDialog = dialog.create()
        alertDialog.show()
    }
}