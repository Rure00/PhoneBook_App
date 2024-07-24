package com.project.phonebook

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.phonebook.fragment.ContractListFragment
import com.project.phonebook.fragment.MyPageFragment

class MainViewPager(fragment: Fragment): FragmentStateAdapter(fragment) {
    //TODO: update list.
    private val pages = listOf<Fragment>(ContractListFragment(), MyPageFragment())
    val pageTag = listOf(ContractListFragment.TAB_NAME, MyPageFragment.TAB_NAME)

    override fun getItemCount() = pages.size
    override fun createFragment(position: Int) = pages[position]
}