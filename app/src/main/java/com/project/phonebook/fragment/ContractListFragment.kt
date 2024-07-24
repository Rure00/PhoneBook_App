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
import com.project.phonebook.adapter.ContractListAdapter
import com.project.phonebook.data.ContractData
import com.project.phonebook.data.`object`.ContractObject
import com.project.phonebook.databinding.FragmentContractListBinding
import com.project.phonebook.dialog.AddContractDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContractListFragment : Fragment() {
    private lateinit var binding: FragmentContractListBinding
    private lateinit var adapter: ContractListAdapter

    companion object {
        const val TAB_NAME = "Contract"
        const val CHANNEL_ID = "call play game notification"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContractListBinding.inflate(inflater)
        adapter = ContractListAdapter(
            onNotificationClick = { contractData ->
                Toast.makeText(
                    context,
                    "${contractData.sendNotificationSec}초 뒤에 ${contractData.userName}님과 함께 게임하기 위한 알람을 보냅니다.",
                    Toast.LENGTH_SHORT
                ).show()

                CoroutineScope(Dispatchers.Main).launch {
                    delay(contractData.sendNotificationSec * 1000L)
                    sendNotification(contractData)
                }
            }
        )
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