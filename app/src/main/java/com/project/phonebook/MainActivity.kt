package com.project.phonebook

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.phonebook.data.DetailTitleData
import com.project.phonebook.databinding.ActivityMainBinding
import com.project.phonebook.fragment.ContactDetailFragment
import com.project.phonebook.fragment.ContractListFragment
import com.project.phonebook.fragment.MainFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction().replace(R.id.main_fcv, MainFragment(), "MAIN")
            .commitNow()

    }

    fun changeFragment(index: Int) {
        when (index) {
            1 -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fcv, ContractListFragment())
                    .commit()
            }
            2-> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fcv, ContactDetailFragment())
                    .commit()
            }
        }
    }

}