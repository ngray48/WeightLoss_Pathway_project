@file:Suppress("DEPRECATION")

package com.example.weightloss_pathway_project

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
@Suppress("DEPRECATION", "DEPRECATION")
// Create adapter for Tab Layout for DayOfWeek
internal class MyTabAdapter(
    var context: Context,
    fm: FragmentManager,
    private var totalTabs: Int
) :
    // Calls the fragment when the tab is pressed
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Sunday()
            }
            1 -> {
                Monday()
            }
            2 -> {
                Tuesday()
            }
            3 -> {
                Wednesday()
            }
            4 -> {
                Thursday()
            }
            5 -> {
                Friday()
            }
            6 -> {
                Saturday()
            }
            else -> getItem(position)
        }
    }

    // Describes the number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}