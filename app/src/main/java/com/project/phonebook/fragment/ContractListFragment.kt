package com.project.phonebook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.phonebook.adapter.ContractListAdapter
import com.project.phonebook.data.`object`.ContractObject
import com.project.phonebook.databinding.FragmentContractListBinding
import com.project.phonebook.dialog.AddContractDialog

class ContractListFragment : Fragment() {
    private lateinit var binding: FragmentContractListBinding
    private lateinit var adapter: ContractListAdapter

    val TAB_NAME = "Contract"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContractListBinding.inflate(inflater)
        adapter = ContractListAdapter()
        val recyclerView = binding.contractRvContractList

        adapter.submitList(ContractObject.getContractList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.addContractIvContractList.setOnClickListener {
            val dialog = AddContractDialog(
                onAcceptClick = {
                    val currentList = adapter.currentList.toMutableList()
                    currentList.add(it)
                    adapter.submitList(currentList)
                }
            )
            dialog.show(parentFragmentManager, "dialog")
        }

        return binding.root
    }
}