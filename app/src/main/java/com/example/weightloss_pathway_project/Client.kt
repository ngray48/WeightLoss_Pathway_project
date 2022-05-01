package com.example.weightloss_pathway_project

import java.io.Serializable

open class Client : Account(), Serializable{
    var isAdmin : Boolean = false
}