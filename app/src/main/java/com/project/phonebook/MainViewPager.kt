package com.project.phonebook

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPager(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val pages = listOf<Fragment>(FirstFragment(), SecondFragment())

    override fun getItemCount() = pages.size
    override fun createFragment(position: Int) = pages[position]



}