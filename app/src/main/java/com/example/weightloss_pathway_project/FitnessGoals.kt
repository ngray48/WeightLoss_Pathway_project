package com.example.weightloss_pathway_project

import java.io.Serializable

class FitnessGoals : Serializable, DefinedGoal() {
    var mode : String = String()
    var intensity : String = String()
    var duration : String = String()

    // Create fitness goal string
    fun goalString() : String{
        return String.format("$mode for $duration minutes at $intensity intensity")
    }
}