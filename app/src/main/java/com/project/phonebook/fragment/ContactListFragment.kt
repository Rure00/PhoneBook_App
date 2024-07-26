package com.project.phonebook.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.phonebook.MainActivity
import com.project.phonebook.R
import com.project.phonebook.adapter.ContactListAdapter
import com.project.phonebook.data.ContractData
import com.project.phonebook.data.`object`.ContactObject
import com.project.phonebook.databinding.FragmentContactListBinding
import com.project.phonebook.dialog.AddContactDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        adapter = ContactListAdapter(
            onNotificationClick = { contactData ->
                Toast.makeText(
                    context,
                    "${contactData.sendNotificationSec}초 뒤에 ${contactData.userName}님과 함께 게임하기 위한 알람을 보냅니다.",
                    Toast.LENGTH_SHORT
                ).show()

                CoroutineScope(Dispatchers.Main).launch {
                    delay(contactData.sendNotificationSec * 1000L)
                    sendNotification(contactData)
                }
            }
        )
        val recyclerView = binding.contactRvContactList

        adapter.submitList(ContactObject.getContactList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.addContactIvContactList.setOnClickListener {
            val dialog = AddContactDialog(
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

    private fun sendNotification(contractData: ContractData) {
        val channel = NotificationChannel(CHANNEL_ID, "calling notification", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "game call channel description"
        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("notificationClick", contractData)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("게임 호출기")
            setContentText("${contractData.userName}님과 게임할 시간입니다!")
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }

        notificationManager.notify(1, builder.build())
    }
}