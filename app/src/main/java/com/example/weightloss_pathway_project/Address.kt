package com.example.weightloss_pathway_project

import java.io.Serializable

class Address : Serializable {
    var street = String()
    var city = String()
    var state = String()
    var zip = String()

    fun addressToDatabaseFire() : String{
        return String.format("$street/$city/$state/$zip")
    }
}

