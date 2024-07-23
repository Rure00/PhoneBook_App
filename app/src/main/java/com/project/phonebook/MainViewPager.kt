package com.project.phonebook

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.phonebook.fragment.ContractListFragment

class MainViewPager(fragment: Fragment): FragmentStateAdapter(fragment) {
    //TODO: update list.
    private val pages = listOf<Fragment>(ContractListFragment())

    override fun getItemCount() = pages.size
    override fun createFragment(position: Int) = pages[position]
}