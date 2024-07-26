package com.project.phonebook.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.project.phonebook.R
import com.project.phonebook.data.ContractData
import com.project.phonebook.data.`object`.ContractObject
import com.project.phonebook.data.`object`.MyPageAccinfo
import com.project.phonebook.databinding.MypageMainLayoutRewriteProfileV1Binding

class MyPageRewriteFragment : Fragment() {

    val TAB_NAME = "My Page Rewrite"

    // 바인딩용 구문 추가 1(박정호)
    private lateinit var MyPageRewBinding: MypageMainLayoutRewriteProfileV1Binding

    // 전달받은 객체용 구문 추가 1(박정호)
    companion object{
        private val rawAccount:String = "myAccount"

        fun pass1Object(myAccount:ContractData): MyPageRewriteFragment {
            val newFragment = MyPageRewriteFragment()
            val boxBox = Bundle()
            boxBox.putParcelable(rawAccount, myAccount)
            newFragment.arguments = boxBox
            return newFragment
        }
    }

    private lateinit var trgAccount: ContractData

    override fun onCreateView(
        inflater : LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View? {
        // 바인딩용 구문 추가 2(박정호)
        MyPageRewBinding = MypageMainLayoutRewriteProfileV1Binding.inflate(layoutInflater)

        // 전달받은 객체용 구문 추가 2(박정호)
        if(arguments != null){
            trgAccount = arguments?.getParcelable(rawAccount)!!

            // 버튼용 전역번수 추가(박정호)
            val btnCompleteProfile:Button = MyPageRewBinding.mypageBtnFixProfile

            // 전달받은 객체 활용 레이아웃 수정(박정호)
            MyPageRewBinding.apply{
                mypageEdittextName.setText(trgAccount.userName)
                mypageEdittextCompany.setText(trgAccount.affiliated)
                mypageEdittextContentsPhone.setText(trgAccount.phoneNumber)
            }

                // 프레그먼트 이동(>> MyPageFragment)(박정호)
                btnCompleteProfile.setOnClickListener {

                    Log.d("MyPageRewriteFragment", "수정된 프로필 전달을 위한 트랜색션 시작")

                    MyPageAccinfo.accName = MyPageRewBinding.mypageEdittextName.text.toString()
                    MyPageAccinfo.accParty = MyPageRewBinding.mypageEdittextCompany.text.toString()
                    MyPageAccinfo.accPhone = MyPageRewBinding.mypageEdittextContentsPhone.text.toString()
                    MyPageAccinfo.accChk = true

                    Log.d("MyPageRewriteFragment", "수정된 프로필 전달을 위한 싱글톤 생성 완료")

                    val newFragment:Fragment = MyPageFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fcv, newFragment)
                        .commit()

                    Log.d("MyPageRewriteFragment", "현재의 프래그먼트 무사히 종료")
                }
            }
        else{
            return MyPageRewBinding.root
        }
        return MyPageRewBinding.root
    }
}