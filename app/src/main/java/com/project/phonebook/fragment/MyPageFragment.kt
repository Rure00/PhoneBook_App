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
import androidx.fragment.app.FragmentTransaction
import com.project.phonebook.R
import com.project.phonebook.data.ContractData
import com.project.phonebook.data.`object`.ContractObject
import com.project.phonebook.databinding.MypageMainLayoutV1Binding

class MyPageFragment : Fragment() {

    val TAB_NAME = "My Page"

    // 바인딩용 구문 추가 1(박정호)
    private lateinit var MyPageStdBinding: MypageMainLayoutV1Binding

    // 전달받은 객체용 구문 추가 1(박정호)
    companion object{
        private val newAccount:String = "trgAccount"

        fun pass2Object(trgAccount:ContractData): MyPageRewriteFragment {
            val newFragment = MyPageRewriteFragment()
            val box = Bundle()
            box.putParcelable(newAccount, trgAccount)
            newFragment.arguments = box
            return newFragment
        }
    }

    val myAccount:ContractData? = ContractObject.getContractList().find { it.userName == "박정호" }
    private lateinit var finAccount: ContractData

    override fun onCreateView(
        inflater : LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View? {
        // 바인딩용 구문 추가 2(박정호)
        MyPageStdBinding = MypageMainLayoutV1Binding.inflate(layoutInflater)

        // 전달받은 객체용 구문 추가 2(박정호)
        if(arguments == null){
            val myName:String = myAccount?.userName ?: "noMan"
            val myParty:String = myAccount?.affiliated ?: "noMan"
            val myPhone:String = myAccount?.phoneNumber ?:"000-0000-0000"

            MyPageStdBinding.mypageTextName.text = myName
            MyPageStdBinding.mypageTextCompany.text = myParty
            MyPageStdBinding.mypageTextContentsPhone.text = myPhone
        }
        else{arguments.let{
            finAccount = it?.getParcelable(newAccount)!!

            val myName:String = finAccount?.userName ?: "noMan"
            val myParty:String = finAccount?.affiliated ?: "noMan"
            val myPhone:String = finAccount?.phoneNumber ?:"000-0000-0000"

            MyPageStdBinding.mypageTextName.text = myName
            MyPageStdBinding.mypageTextCompany.text = myParty
            MyPageStdBinding.mypageTextContentsPhone.text = myPhone
        }}



        // 활성화된 계정 선택하는 로직 추가 필요??(박정호)



        // 버튼용 전역번수 추가(박정호)
        val btnRewriteProfile:Button = MyPageStdBinding.mypageBtnFixProfile

        // 프레그먼트 이동(>> MyPageRewriteFragment)(박정호)
        btnRewriteProfile.setOnClickListener {
            Log.d("MyPageFragment", "프로필 수정을 위한 트랜색션 시작")

            val newFragment:Fragment = MyPageRewriteFragment.pass1Object(myAccount!!)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fcv, newFragment)
                ?.commitNow()

            Log.d("MyPageFragment", "프로필 수정을 위한 트랜색션 완료")
        }

//        btnRewriteProfile.setOnClickListener {
//            Log.d("MyPageFragment", "프로필 수정을 위한 트랜색션 시작")
//
//            val newFragment:Fragment = MyPageRewriteFragment.pass1Object(myAccount!!)
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.main_fcv, newFragment)
//                .addToBackStack(null)
//                .commit()
//
//            Log.d("MyPageFragment", "프로필 수정을 위한 트랜색션 완료")
//        }

        return MyPageStdBinding.root
    }
}