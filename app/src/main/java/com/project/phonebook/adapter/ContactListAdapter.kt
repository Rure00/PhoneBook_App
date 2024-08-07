package com.project.phonebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.phonebook.R
import com.project.phonebook.data.ContactData
import com.project.phonebook.databinding.ItemContactListBinding

class ContactListAdapter(private val clickListener: ClickListener): ListAdapter<ContactData, ContactListAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<ContactData>() {
        override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_list, parent, false)
        return ViewHolder(ItemContactListBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(val binding: ItemContactListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContactData, position: Int) {
            with(binding) {
                contactItemIvProfile.setImageResource(contact.profile)
                contactItemTvUserName.text = contact.userName
                contactItemTvAffiliated.text = contact.affiliated
                contactItemIvFavorite.setColorFilter(
                    if (contact.isFavorite) R.color.mainTurquoise
                    else R.color.mainBlack
                )

                contactItemIvNotification.setOnClickListener {
                    clickListener.onNotificationClick(contact)
                }

                root.setOnClickListener {
                    clickListener.onItemClicked(position)
                }
            }
        }
    }

    interface ClickListener {
        fun onItemClicked(position: Int)
        fun onNotificationClick(data: ContactData)
    }
}