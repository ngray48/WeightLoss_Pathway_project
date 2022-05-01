package com.example.weightloss_pathway_project

import java.util.regex.Pattern

class Email {
    private var userEmail : String = String()
    var isEmail : Boolean = false

    // Check for valid email
    fun isValidEmail(email: String): Boolean {
        val emailString = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
         isEmail = Pattern.compile(emailString).matcher(email).matches()

        return if (isEmail){
            userEmail = email
            true
        } else{
            false
        }
    }
}