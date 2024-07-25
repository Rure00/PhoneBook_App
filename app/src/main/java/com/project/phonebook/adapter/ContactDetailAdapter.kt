package com.project.phonebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.phonebook.data.DetailTitleData
import com.project.phonebook.databinding.ItemDetailRecyclerviewBinding

class ContactDetailAdapter(val titleList: MutableList<DetailTitleData>) :
    RecyclerView.Adapter<ContactDetailAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactDetailAdapter.Holder {
        val binding = ItemDetailRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ContactDetailAdapter.Holder, position: Int) {
        holder.listTitle.text = titleList[position].title
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    inner class Holder(val binding: ItemDetailRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val listTitle = binding.detailRvTitle
    }

}
