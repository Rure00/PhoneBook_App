package com.project.phonebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.phonebook.databinding.MypageMainLayoutBinding

class MyPageFragment : Fragment() {

    // 바인딩용 구문 추가 1(박정호)
    private lateinit var MyPageBinding: MypageMainLayoutBinding

    override fun onCreateView(
        inflater : LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View? {
        // 바인딩용 구문 추가 2(박정호)
        MyPageBinding = MypageMainLayoutBinding.inflate(layoutInflater)


        return inflater.inflate(R.layout.mypage_main_layout, container, false)
    }
}