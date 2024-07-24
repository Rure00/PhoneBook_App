package com.project.phonebook

import android.content.Intent
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
import com.project.phonebook.data.ContractData
import com.project.phonebook.databinding.ActivityMainBinding
import com.project.phonebook.fragment.MainFragment

class MainActivity : AppCompatActivity() {
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

        val notificationExtraData = intent.getParcelableExtra("notificationClick", ContractData::class.java)
        if (notificationExtraData != null)
            supportFragmentManager.beginTransaction().replace(R.id.main_fcv, ContactDetailFragment()).commitNow()
        else supportFragmentManager.beginTransaction().replace(R.id.main_fcv, MainFragment(), "MAIN").commitNow()
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
}