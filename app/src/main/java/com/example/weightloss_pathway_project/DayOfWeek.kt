package com.example.weightloss_pathway_project

class DayOfWeek {
    var dd = 0
    var mm = 0
    var yyyy = 0
    private var monthValues = arrayListOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    private fun checkLeap(y: Int): Int {
        return if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) 1 else 0
    }

    fun calculate(): String {

        // Checking Leap year. If true then 1 else 0.
        val flagForLeap = checkLeap(yyyy)

        /*Declaring and initialising the given information
         * and arrays*/
        val day = arrayOf(
            "Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday",
            "Saturday"
        )
        val mNo = intArrayOf(0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5)

        /*Generalised check to find any Year Value*/
        val j: Int = if (yyyy / 100 % 2 == 0) {
            if (yyyy / 100 % 4 == 0) 6 else 2
        } else {
            if ((yyyy / 100 - 1) % 4 == 0) 4 else 0
        }

        /*THE FINAL FORMULA*/
        val total = (yyyy % 100 + yyyy % 100 / 4 + dd
                + mNo[mm - 1] + j)
        return if (flagForLeap == 1) {
            if (total % 7 > 0) (day[total % 7 - 1]) else (day[6])
        } else (day[total % 7])
    }

    // Find day of the week
    fun findDayOfWeek(date: String): String {
        val splitDate = date.replace(",", "").split(" ")
        val dates = Date()
        dates.month = splitDate[0]
        dates.numMonth = dates.monthToNumber(splitDate[0])
        dates.day = splitDate[1].toInt()
        dates.year = splitDate[2].toInt()
        dates.dayOfWeek = splitDate[3]
        dates.numDayOfWeek = dates.dayToNumber(splitDate[3])

        return getSunday(dates)
    }

    // Find Sunday of the week selected
    private fun getSunday(date: Date): String {

        val dayValue = when (date.numDayOfWeek) {
            0 -> date.day
            1 -> date.day - 1
            2 -> date.day - 2
            3 -> date.day - 3
            4 -> date.day - 4
            5 -> date.day - 5
            6 -> date.day - 6
            else -> 99
        }

        date.day = dayValue

        if (date.day < 0) {
            date.day = monthValues[date.numMonth - 1] + date.day + 1
            date.numMonth = date.numMonth - 1
            if (date.numMonth <= 0) {
                date.numMonth = 12
                date.day = monthValues[date.numMonth - 1] + dayValue
                date.year = date.year - 1
            }
        }

        return "${date.monthToString(date.numMonth)} ${date.day}, ${date.year}, Sunday"
    }

    // Find next day relative to other date
    fun nextDay(date: String): String {
        val splitDate = date.replace(",", "").split(" ")
        val dates = Date()
        dates.month = splitDate[0]
        dates.numMonth = dates.monthToNumber(splitDate[0])
        dates.day = splitDate[1].toInt()
        dates.year = splitDate[2].toInt()
        dates.dayOfWeek = splitDate[3]
        dates.numDayOfWeek = dates.dayToNumber(splitDate[3])

        dates.day = dates.day + 1
        dates.numDayOfWeek = dates.numDayOfWeek + 1
        val da = Date()
        dates.dayOfWeek = da.dayToString(dates.numDayOfWeek)
        if(dates.numDayOfWeek > 6){
            dates.numDayOfWeek = 0
        }

        if (dates.day > monthValues[dates.numMonth - 1]) {
            dates.day = dates.day - monthValues[dates.numMonth - 1]
            dates.numMonth = dates.numMonth + 1
            if (dates.numMonth > 12) {
                dates.numMonth = 1
                dates.year = dates.year + 1

            }
        }
        return "${dates.monthToString(dates.numMonth)} ${dates.day}, ${dates.year}, ${dates.dayOfWeek}"
    }
}