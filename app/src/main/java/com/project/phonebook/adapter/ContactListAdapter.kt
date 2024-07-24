package com.project.phonebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.phonebook.R
import com.project.phonebook.data.ContractData
import com.project.phonebook.databinding.ItemContactListBinding

class ContactListAdapter(private val onNotificationClick: (ContractData) -> Unit): ListAdapter<ContractData, ContactListAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<ContractData>() {
        override fun areItemsTheSame(oldItem: ContractData, newItem: ContractData): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ContractData, newItem: ContractData): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_list, parent, false)
        return ViewHolder(ItemContactListBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemContactListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContractData) {
            with(binding) {
                contactItemIvProfile.setImageResource(contact.profile)
                contactItemTvUserName.text = contact.userName
                contactItemTvAffiliated.text = contact.affiliated
                contactItemIvFavorite.setColorFilter(
                    if (contact.isFavorite) R.color.mainTurquoise
                    else R.color.mainBlack
                )

                contactItemIvNotification.setOnClickListener {
                    onNotificationClick(contact)
                }
            }
        }
    }
}