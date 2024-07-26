package com.project.phonebook.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.phonebook.MainActivity
import com.project.phonebook.R
import com.project.phonebook.adapter.ContactListAdapter
import com.project.phonebook.data.ContactData
import com.project.phonebook.data.NotificationMessageData
import com.project.phonebook.data.`object`.ContactObject
import com.project.phonebook.data.`object`.MessageObject
import com.project.phonebook.data.`object`.MyPageAccinfo
import com.project.phonebook.databinding.FragmentContactListBinding
import com.project.phonebook.dialog.AddContactDialog
import com.project.phonebook.dialog.SendNotificationMessageDialog
import com.project.phonebook.dialog.LoadContactDialog

class ContactListFragment : Fragment() {
    private lateinit var binding: FragmentContactListBinding
    private lateinit var adapter: ContactListAdapter
    companion object {
        const val TAB_NAME = "Contact"
        const val CHANNEL_ID = "call play game notification"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactListBinding.inflate(inflater)

        val currentUserName = MyPageAccinfo.accName
        val filterContactData = MessageObject.checkMyMessage(currentUserName.toString())
        for (i in 0 until filterContactData.size) {
            sendNotification(i + 1, filterContactData[i])
        }

        adapter = ContactListAdapter(object: ContactListAdapter.ClickListener {
            override fun onItemClicked(position: Int) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.main_fcv, ContactDetailFragment(adapter.currentList[position])).commitNow()
            }
            override fun onNotificationClick(data: ContactData) {
                // data: receiver
                // 받아온 parcelable data: sender
                val sendNotificationMessageDialog = SendNotificationMessageDialog(
                    sender = currentUserName.toString(),
                    receiver = data.userName
                )
                sendNotificationMessageDialog.show(parentFragmentManager, "send notification dialog")
            }
        })
        val recyclerView = binding.contactRvContactList
        adapter.submitList(ContactObject.getContactList())

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.addContactIvContactList.setOnClickListener {
            val dialog = AddContactDialog(
                onAcceptClick = {
                    val curList = adapter.currentList.toMutableList()
                    curList.add(it)
                    adapter.submitList(curList)
                }
            )
            dialog.show(parentFragmentManager, "dialog")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val activity = activity as MainActivity
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fcv, LoginFragment()).commitNow()
            }
        })

        LoadContactDialog(object: LoadContactDialog.DismissListener {
            override fun onDismiss(list: List<ContactData>) {
                val curList = adapter.currentList.toMutableList()
                curList.addAll(list)
                adapter.submitList(curList)
            }

        }).show(childFragmentManager, "LoadContact")
    }

    private fun sendNotification(id: Int, messageData: NotificationMessageData) {
        val contactData = ContactObject.getContactList().first { it.userName == messageData.sender }
        Log.d("TAG", "sendNotification: $contactData")

        val channel = NotificationChannel(CHANNEL_ID, "calling notification", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "game call channel description"
        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("notificationClick", contactData)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("${messageData.sender}님에게 온 메시지")
            setContentText(messageData.message)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }

        notificationManager.notify(id, builder.build())
    }
}