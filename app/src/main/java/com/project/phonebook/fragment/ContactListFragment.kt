package com.project.phonebook.fragment

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
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
import com.project.phonebook.data.ContactData
import com.project.phonebook.data.`object`.ContactObject
import com.project.phonebook.databinding.FragmentContactListBinding
import com.project.phonebook.dialog.AddContactDialog
import com.project.phonebook.dialog.LoadContactDialog
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
        adapter = ContactListAdapter(object: ContactListAdapter.ClickListener {
            override fun onItemClicked(position: Int) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.main_fcv, ContactDetailFragment(adapter.currentList[position])).commitNow()
            }
            override fun onNotificationClick(data: ContactData) {
                Toast.makeText(
                    context,
                    "${data.sendNotificationSec}초 뒤에 ${data.userName}님과 함께 게임하기 위한 알람을 보냅니다.",
                    Toast.LENGTH_SHORT
                ).show()

                CoroutineScope(Dispatchers.Main).launch {
                    delay(data.sendNotificationSec * 1000L)
                    sendNotification(data)
                }
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

        LoadContactDialog(object: LoadContactDialog.DismissListener {
            override fun onDismiss(list: List<ContactData>) {
                val curList = adapter.currentList.toMutableList()
                curList.addAll(list)
                adapter.submitList(curList)
            }

        }).show(childFragmentManager, "LoadContact")
    }

    private fun sendNotification(contactData: ContactData) {
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
            setContentTitle("게임 호출기")
            setContentText("${contactData.userName}님과 게임할 시간입니다!")
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }

        notificationManager.notify(1, builder.build())
    }
}