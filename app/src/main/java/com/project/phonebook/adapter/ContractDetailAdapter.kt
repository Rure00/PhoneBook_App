package com.project.phonebook.adapter

import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.phonebook.R
import com.project.phonebook.adapter.ContractListAdapter.ViewHolder
import com.project.phonebook.data.DetailTitleData
import com.project.phonebook.databinding.DetailItemRecyclerviewBinding
import com.project.phonebook.databinding.FragmentContractDetailBinding
import com.project.phonebook.databinding.ItemContractListBinding

class ContractDetailAdapter(val titleList: MutableList<DetailTitleData>) :
    RecyclerView.Adapter<ContractDetailAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContractDetailAdapter.Holder {
        val binding = DetailItemRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ContractDetailAdapter.Holder, position: Int) {
        holder.listTitle.text = titleList[position].title
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    inner class Holder(val binding: DetailItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val listTitle = binding.detailRvTitle
    }

}
