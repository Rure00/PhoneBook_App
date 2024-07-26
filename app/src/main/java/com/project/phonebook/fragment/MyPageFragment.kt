package com.project.phonebook.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.project.phonebook.R
import com.project.phonebook.data.ContactData
import com.project.phonebook.data.`object`.ContactObject
import com.project.phonebook.databinding.MypageMainLayoutV1Binding

class MyPageFragment : Fragment() {

    companion object {
        const val TAB_NAME = "My Page"
    }


    // 바인딩용 구문 추가 1(박정호)
    private lateinit var MyPageStdBinding: MypageMainLayoutV1Binding

    val myAccount:ContactData? = ContactObject.getContactList().find { it.userName == "박정호" }
    private var chkMode: Boolean = false

    override fun onCreateView(
        inflater : LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View? {

        Log.d("MyPageFragment", "onCreateView 상태 시작")

        // 바인딩용 구문 추가 2(박정호)
        MyPageStdBinding = MypageMainLayoutV1Binding.inflate(layoutInflater)

        // 모션 레이아웃 상태 고정을 위한 변수 추가(박정호)
        val motAnchor:MotionLayout = MyPageStdBinding.mypagePrinceLayout

        // 버튼용 번수 추가(박정호)
        val btnRewriteProfile:Button = MyPageStdBinding.mypageBtnFixProfile

        // 텍스트뷰 변수 추가 및 초기화(박정호)
        val tvName:TextView = MyPageStdBinding.mypageTextName
        tvName.text = myAccount?.userName
        val tvParty:TextView = MyPageStdBinding.mypageTextCompany
        tvParty.text = myAccount?.affiliated
        val tvPhone:TextView = MyPageStdBinding.mypageTextContentsPhone
        tvPhone.text = myAccount?.phoneNumber

        // 에딧텍스트뷰 변수 추가 및 초기화(박정호)
        val etvName:EditText = MyPageStdBinding.mypageEdittextName
        etvName.setText(myAccount?.userName)
        val etvParty:EditText = MyPageStdBinding.mypageEdittextCompany
        etvParty.setText(myAccount?.affiliated)
        val etvPhone:EditText = MyPageStdBinding.mypageEdittextContentsPhone
        etvPhone.setText(myAccount?.phoneNumber)


        // 프레그먼트 이동(>> MyPageRewriteFragment)(박정호)
        // 프로필 수정는 로직 추가(박정호)
        btnRewriteProfile.setOnClickListener {
            Log.d("MyPageFragment", "프로필 수정버튼 동작 시작")

            // 모션 레이아웃 상태 고정
            motAnchor.transitionToEnd()

            // 수정하기 클릭 시
            if(chkMode == false){
                Log.d("MyPageFragment", "프로필 수정하기 버튼 로직 시작")

                // 모드 변환
                chkMode = true

                // 텍스트뷰 비활성화
                tvName.visibility = View.INVISIBLE
                tvParty.visibility = View.INVISIBLE
                tvPhone.visibility = View.INVISIBLE

                // 에딧텍스트뷰 활성화
                etvName.visibility = View.VISIBLE
                etvParty.visibility = View.VISIBLE
                etvPhone.visibility = View.VISIBLE

                // 버튼 이름 변경
                btnRewriteProfile.text = "프로필 수정완료"

                Log.d("MyPageFragment", "프로필 수정하기 버튼 로직 종료")
            }
            // 수정완료 클릭 시
            else{
                Log.d("MyPageFragment", "프로필 수정완료 버튼 로직 시작")

                // 모드 변환
                chkMode = false

                // 에디트뷰 비활성화
                etvName.visibility = View.INVISIBLE
                etvParty.visibility = View.INVISIBLE
                etvPhone.visibility = View.INVISIBLE

                // 텍스트뷰 초기화 및 활성화
                tvName.text = etvName.text
                tvName.visibility = View.VISIBLE
                tvParty.text = etvParty.text
                tvParty.visibility = View.VISIBLE
                tvPhone.text = etvPhone.text
                tvPhone.visibility = View.VISIBLE

                // 버튼 이름 변경
                btnRewriteProfile.text = "프로필 수정하기"

                // 객체 값 변경
                myAccount?.userName = etvName.text.toString()
                myAccount?.affiliated = etvParty.text.toString()
                myAccount?.phoneNumber = etvPhone.text.toString()

                Log.d("MyPageFragment", "프로필 수정완료 버튼 로직 종료")
            }

            Log.d("MyPageFragment", "프로필 수정버튼 동작 종료")
        }

        Log.d("MyPageFragment", "onCreateView 상태 종료")

        // 뒤로가기 기능 추가
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("MyPageFragment", "뒤로가기 버튼 눌림")

                (parentFragment as MainFragment).requireView().findViewById<ViewPager2>(R.id.main_vp).currentItem = 0

            }
        })

        return MyPageStdBinding.root
    }

    override fun onResume() {
        super.onResume()

        Log.d("MyPageFragment", "onResume 상태 시작")
        MyPageStdBinding = MypageMainLayoutV1Binding.inflate(layoutInflater)

        // 레이아웃 최신화
        MyPageStdBinding.root.invalidate()

        Log.d("MyPageFragment", "onResume 상태 종료")
    }
}


//        Log.d("MyPageFragment", "되는건가!")
//
//        // 전달받은 객체 활용 레이아웃 수정(박정호)
//        // MyPageRewrite에서 수정받은 내용이 있을 때(싱글톤)
//        if(MyPageAccinfo.accChk == true){
//            Log.d("MyPageFragment", "프로필 수정됬어요(싱글톤)!!")
//
//            MyPageStdBinding.apply{
//                mypageTextName.text = MyPageAccinfo.accName
//                mypageTextCompany.text = MyPageAccinfo.accParty
//                mypageTextContentsPhone.text = MyPageAccinfo.accPhone
//            }
//
//            Log.d("MyPageFragment", "${MyPageStdBinding.mypageTextName.text}")
////            MyPageAccinfo.accChk = false
//            chkAcc = 1
//        }
//        // MyPageRewrite에서 수정받은 내용이 있을 때(번들)
//        else if(arguments != null){
//            Log.d("MyPageFragment", "프로필 수정됬어요(번들)!!")
////            finAccount = arguments?.getParcelable(newAccount)!!
////
////            MyPageStdBinding.apply{
////                mypageTextName.text = finAccount.userName
////                mypageTextCompany.text = finAccount.affiliated
////                mypageTextContentsPhone.text = finAccount.phoneNumber
////            }
//            chkAcc = 2
//        }
//        // MyPageRewrite에서 수정받은 내용이 없을 때, 최초 로그인 했을 때
//        else{
//            Log.d("MyPageFragment", "프로필 수정안됬어요!!")
//            MyPageStdBinding.apply{
//                mypageTextName.text = myAccount?.userName ?: "사용자가 특정되지 않음"
//                mypageTextCompany.text = myAccount?.affiliated ?: "사용자가 특정되지 않음"
//                mypageTextContentsPhone.text = myAccount?.phoneNumber ?:"사용자가 특정되지 않음"
//            }
//            chkAcc = 3
//        }



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


//            val newFragment:Fragment = MyPageRewriteFragment.pass1Object(myAccount!!)
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.add(R.id.main_fcv, newFragment)
//                ?.commit()
//
//            Log.d("MyPageFragment", "프로필 수정을 위한 트랜색션 완료")


// 전달받은 객체 활용 레이아웃 수정(박정호)
// MyPageRewrite에서 수정받은 내용이 있을 때(싱글톤)
//        if(MyPageAccinfo.accChk == true){
//            Log.d("MyPageFragment", "프로필 수정됬어요(싱글톤)!!")
//
//            MyPageStdBinding.apply{
//                mypageTextName.text = MyPageAccinfo.accName
//                mypageTextCompany.text = MyPageAccinfo.accParty
//                mypageTextContentsPhone.text = MyPageAccinfo.accPhone
//            }
//
//            Log.d("MyPageFragment", "${MyPageStdBinding.mypageTextName.text}")
//            MyPageAccinfo.accChk = false
//            chkAcc = 1
//        }
// MyPageRewrite에서 수정받은 내용이 있을 때(번들)
//        else if(arguments != null){
//            Log.d("MyPageFragment", "프로필 수정됬어요(번들)!!")
//            finAccount = arguments?.getParcelable(newAccount)!!
//
//            MyPageStdBinding.apply{
//                mypageTextName.text = finAccount.userName
//                mypageTextCompany.text = finAccount.affiliated
//                mypageTextContentsPhone.text = finAccount.phoneNumber
//            }
//            chkAcc = 2
//        }
// MyPageRewrite에서 수정받은 내용이 없을 때, 최초 로그인 했을 때
//        else{
//            Log.d("MyPageFragment", "프로필 수정안됬어요!!")
//            MyPageStdBinding.apply{
//                mypageTextName.text = myAccount?.userName ?: "사용자가 특정되지 않음"
//                mypageTextCompany.text = myAccount?.affiliated ?: "사용자가 특정되지 않음"
//                mypageTextContentsPhone.text = myAccount?.phoneNumber ?:"사용자가 특정되지 않음"
//            }
//            chkAcc = 3
//        }

// 활성화된 계정 선택하는 로직 추가 필요??(박정호)



// 전달받은 객체용 구문 추가 1(박정호)
//    companion object{
//        private val newAccount:String = "trgAccount"
//
//        fun pass2Object(trgAccount:ContractData): MyPageRewriteFragment {
//            val newFragment = MyPageRewriteFragment()
//            val box = Bundle()
//            box.putParcelable(newAccount, trgAccount)
//            newFragment.arguments = box
//            return newFragment
//        }
//    }



//    private lateinit var finAccount: ContractData
//    lateinit var nowAccount:ContractData


//    private var chkAcc: Int = 0
