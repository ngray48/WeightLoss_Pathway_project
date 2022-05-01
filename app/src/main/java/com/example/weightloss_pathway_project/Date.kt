package com.example.weightloss_pathway_project

class Date {
    var month : String = String()
    var numMonth: Int = 0
    var day : Int = 0
    var year : Int = 0
    var dayOfWeek : String = String()
    var numDayOfWeek : Int = 0

    fun dayToString(num : Int) : String {
        val day = when (num) {
            0 -> "Sunday"
            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wednesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            else -> "Unknown"
        }
        return day
    }

    fun dayToNumber(day : String) : Int {
        val num = when (day) {
            "Sunday" -> 0
            "Monday" -> 1
            "Tuesday" -> 2
            "Wednesday" -> 3
            "Thursday" -> 4
            "Friday" -> 5
            "Saturday" -> 6
            else -> 7
        }
        return num
    }

    fun monthToString(num : Int): String{
        val month = when (num - 1){
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> "Unknown"
        }
        return month
    }

    fun monthToNumber(day : String) : Int {
        val month = when (day) {
            "January" -> 1
            "February" -> 2
            "March" -> 3
            "April" -> 4
            "May" -> 5
            "June" -> 6
            "July" -> 7
            "August" -> 8
            "September" -> 9
            "October" -> 10
            "November" -> 11
            "December" -> 12
            else -> 0
        }
        return month
    }

}