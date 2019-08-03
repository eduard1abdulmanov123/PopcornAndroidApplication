package com.abdulmanov.customviews.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class CustomFragmentPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTittleList = mutableListOf<String>()

    override fun getItem(p0: Int) = fragmentList[p0]

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int) =fragmentTittleList[position]

    fun addFragment(fragment: Fragment, title:String):CustomFragmentPagerAdapter{
        fragmentList.add(fragment)
        fragmentTittleList.add(title)
        return this
    }
}