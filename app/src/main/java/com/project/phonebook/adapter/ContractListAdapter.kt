package com.project.phonebook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.phonebook.R
import com.project.phonebook.data.ContractData
import com.project.phonebook.databinding.ItemContractListBinding

class ContractListAdapter: ListAdapter<ContractData, ContractListAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<ContractData>() {
        override fun areItemsTheSame(oldItem: ContractData, newItem: ContractData): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ContractData, newItem: ContractData): Boolean = oldItem == newItem
    }
) {

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contract_list, parent, false)
        return ViewHolder(ItemContractListBinding.bind(view))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.contents.setOnClickListener{
            itemClick?.onClick(it,position)
        }
    }

    inner class ViewHolder(private val binding: ItemContractListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(contract: ContractData) {
            with(binding) {
                contractItemIvProfile.setImageResource(contract.profile)
                contractItemTvUserName.text = contract.userName
                contractItemTvAffiliated.text = contract.affiliated
            }
        }

        val contents = binding.contractItemTvUserName
    }
}