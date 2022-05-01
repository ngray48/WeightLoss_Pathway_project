package com.example.weightloss_pathway_project

import java.io.Serializable

open class Account : Serializable{
    var firstname : String = String()
    var lastname : String = String()
    var address : String = String()
    var email : String = String()
    var phone : String = String()
    var birthday : String = String()
    var password : String = String()

    fun checkName(first: String) : String {
        // New mutable list for modified strings
        val newSplit : ArrayList<String> = ArrayList()
        // Will run if first is not null
        if (first != "") {
            // Separate each value with by delimiter space
            val splitting = first.split(" ")
            // Capitalize for letter in each word
            for (sp in splitting){
                val upper = sp.replaceFirstChar { sp[0].uppercase() }
                // Add word to Array list "newSplit"
                newSplit.add(upper)
            }
        }
        // Return the arraylist to a string and trim white space
        return newSplit.joinToString(" ").trim()
    }
}

