package com.project.phonebook.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.phonebook.adapter.ContractListAdapter
import com.project.phonebook.data.ContractData
import com.project.phonebook.data.`object`.ContractObject
import com.project.phonebook.databinding.FragmentContractListBinding
import com.project.phonebook.dialog.AddContractDialog
import com.project.phonebook.dialog.LoadContactDialog

class ContractListFragment : Fragment() {
    private lateinit var binding: FragmentContractListBinding
    private lateinit var adapter: ContractListAdapter
    private val contactList = mutableListOf<ContractData>()

    companion object {
        const val TAB_NAME = "Contract"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContractListBinding.inflate(inflater)
        adapter = ContractListAdapter()
        val recyclerView = binding.contractRvContractList

        contactList.addAll(ContractObject.getContractList())
        adapter.submitList(contactList)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoadContactDialog(object: LoadContactDialog.DismissListener {
            override fun onDismiss(list: List<ContractData>) {
                Log.d("ContractListFragment", "Get Contact List: ${list.size}")
                val exSize = contactList.size
                contactList.addAll(list)
                binding.contractRvContractList.adapter!!.notifyItemRangeInserted(exSize, list.size)
            }

        }).show(childFragmentManager, "LoadContact")
    }
}