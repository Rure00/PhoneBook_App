package com.project.phonebook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayoutMediator
import com.project.phonebook.MainViewPager
import com.project.phonebook.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
                     get() = _binding!!
    private val mainViewPager by lazy {
        MainViewPager(this)
    }
    private lateinit var parentFM: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFM = parentFragmentManager

        binding.mainVp.adapter = mainViewPager
        TabLayoutMediator(binding.mainLoTab, binding.mainVp) { tab, position ->
            tab.text = "Fragment ${position + 1}"
        }.attach()
    }
}