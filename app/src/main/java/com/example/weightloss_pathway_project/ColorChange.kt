package com.example.weightloss_pathway_project

class ColorChange {
    var color : String = ""

    fun defineThemeColor(color : String) : String {
        val theme = when(color){
            "Red" -> "#FF0000"
            "Orange" -> "#FFA500"
            "Yellow" -> "#F9DB24"
            "Green" -> "#006400"
            "Blue" -> "#006990"
            "Purple" -> "#3700B3"
            else -> "#006990"
        }
        return theme
    }
}