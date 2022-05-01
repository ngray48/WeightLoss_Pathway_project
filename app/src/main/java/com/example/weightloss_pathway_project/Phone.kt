package com.example.weightloss_pathway_project

import android.telephony.PhoneNumberUtils.isGlobalPhoneNumber

class Phone {
    private var area : String = String()
    private var prefix : String = String()
    private var lineNum : String = String()
    var phoneNumber : String = String()
    var isNumber : Boolean = false

    fun isValidPhone(phone : String) : Boolean {
        return isGlobalPhoneNumber(phone)
    }

    fun phoneBreakDown(phone : String) {
        area = phone.slice(0..2)
        prefix = phone.slice(3..5)
        lineNum = phone.slice((6..9))
    }

    fun phoneToDatabaseFire() : String {
        return String.format("$area/$prefix/$lineNum")
    }
}