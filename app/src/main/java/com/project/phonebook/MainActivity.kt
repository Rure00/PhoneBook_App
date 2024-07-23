package com.project.phonebook

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.phonebook.databinding.MypageMainBinding
import com.project.phonebook.databinding.MypageMainLayoutBinding

class MainActivity : AppCompatActivity() {

    // 바인딩용 구문 추가 1(박정호)
    private lateinit var MypageTabBinding: MypageMainBinding
    private lateinit var MypageViewpager2Binding: MypageMainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바인딩용 구문 추가 2(박정호)
        MypageTabBinding = MypageMainBinding.inflate(layoutInflater)
        setContentView(MypageTabBinding.root)
        MypageViewpager2Binding = MypageMainLayoutBinding.inflate(layoutInflater)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mypage_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 뷰페이저 및 탭 레이아웃 적용 구문(박정호)
        val ViewPager: ViewPager2 = MypageTabBinding.mypageViewpager2
        val TabLayout: TabLayout = MypageTabBinding.mypageTablayout

        val adapter = MypageViewPagerAdapter(this)
        ViewPager.adapter = adapter

        TabLayoutMediator(TabLayout, ViewPager){
            tab, position -> tab.text = "목요일"
        }.attach()

        enableEdgeToEdge()
    }
}