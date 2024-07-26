package com.project.phonebook

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.phonebook.fragment.ContactDetailFragment
import com.project.phonebook.data.ContactData
import com.project.phonebook.databinding.ActivityMainBinding
import com.project.phonebook.fragment.FragmentCallDataListener
import com.project.phonebook.fragment.FragmentMessageDataListener
import com.project.phonebook.fragment.MainFragment

class MainActivity : AppCompatActivity(), FragmentCallDataListener, FragmentMessageDataListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initNotificationPermission()

        supportFragmentManager.beginTransaction().add(R.id.main_fcv, MainFragment(), "MAIN")
            .commitNow()
        val notificationExtraData =
            intent.getParcelableExtra("notificationClick", ContactData::class.java)
        if (notificationExtraData != null) {
            val contactDetailFragment = ContactDetailFragment(notificationExtraData)
            supportFragmentManager.beginTransaction().add(R.id.main_fcv, contactDetailFragment)
                .commitNow()
        }
    }

    private fun initNotificationPermission() {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
        }
    }

    override fun onCallDataReceived(callOn: Boolean, callNum: String) {
        if (callOn == true) {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${callNum}")
            startActivity(callIntent)
        }

    }

    override fun onMessageDataReceived(
        messageOn: Boolean,
        messageNum: String,
        messageTargetUser: String
    ) {
        if (messageOn == true) {
            val smsUri = Uri.parse("smsto:${messageNum}")
            val messageIntent = Intent(Intent.ACTION_SENDTO)
            messageIntent.setData(smsUri)
            messageIntent.putExtra("sms_body", "${messageTargetUser}님에게 알람을 보냅니다!")
            startActivity(messageIntent)
        }
    }

}