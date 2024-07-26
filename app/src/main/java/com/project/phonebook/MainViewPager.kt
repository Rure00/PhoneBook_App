package com.project.phonebook

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.phonebook.fragment.ContactListFragment
import com.project.phonebook.fragment.MyPageFragment

class MainViewPager(fragment: Fragment): FragmentStateAdapter(fragment) {
    //TODO: update list.
    private val pages = listOf<Fragment>(ContactListFragment(), MyPageFragment())

    override fun getItemCount() = pages.size
    override fun createFragment(position: Int) = pages[position]
}