package com.project.phonebook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.phonebook.R
import com.project.phonebook.adapter.ContractDetailAdapter
import com.project.phonebook.adapter.ContractListAdapter
import com.project.phonebook.data.DetailTitleData
import com.project.phonebook.data.`object`.ContractObject
import com.project.phonebook.databinding.FragmentContractDetailBinding
import com.project.phonebook.databinding.FragmentContractListBinding
import com.project.phonebook.databinding.FragmentMainBinding
import com.project.phonebook.dialog.AddContractDialog


class ContractDetailFragment : Fragment() {
    private lateinit var binding: FragmentContractDetailBinding
    private lateinit var adapter: ContractDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContractDetailBinding.inflate(inflater)

        setRecyclerView()
        return binding.root

    }

    private fun setRecyclerView() {
        val titleList = mutableListOf<DetailTitleData>()
        titleList.add(DetailTitleData("Phone Number"))
        titleList.add(DetailTitleData("Event"))
        titleList.add(DetailTitleData("E-mail Address"))

        adapter = ContractDetailAdapter(titleList)
        binding.detailRecyclerview.adapter = adapter
        binding.detailRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}