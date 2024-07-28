package com.project.phonebook.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.project.phonebook.R
import com.project.phonebook.data.ContactData
import com.project.phonebook.data.`object`.ContactObject
import com.project.phonebook.data.`object`.MyPageAccinfo
import com.project.phonebook.databinding.MypageMainLayoutV1Binding

class MyPageFragment : Fragment() {

    companion object {
        const val TAB_NAME = "My Page"
    }


    // 바인딩용 구문 추가 1(박정호)
    private lateinit var MyPageStdBinding: MypageMainLayoutV1Binding

    val myAccount:ContactData? = ContactObject.getContactList().find { it.userName == MyPageAccinfo.accName }
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

        // 버튼용 변수 추가(박정호)
        val btnRewriteProfile:Button = MyPageStdBinding.mypageBtnFixProfile

        // 이미지뷰 변수 추가(박정호)
        var ivUser:ImageView = MyPageStdBinding.mypageImgProfileMain
        ivUser.setImageResource(myAccount?.profile ?: R.drawable.mypage_prf_img)

        // 텍스트뷰 변수 추가 및 초기화(박정호)
        val tvName:TextView = MyPageStdBinding.mypageTextName
        tvName.text = myAccount?.userName
        val tvParty:TextView = MyPageStdBinding.mypageTextCompany
        tvParty.text = myAccount?.affiliated
        val tvPhone:TextView = MyPageStdBinding.mypageTextContentsPhone
        tvPhone.text = myAccount?.phoneNumber

        // 에딧텍스트뷰 변수 추가 및 초기화(박정호)
//        val etvName:EditText = MyPageStdBinding.mypageEdittextName
//        etvName.setText(myAccount?.userName)
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
//                tvName.visibility = View.INVISIBLE
                tvParty.visibility = View.INVISIBLE
                tvPhone.visibility = View.INVISIBLE

                // 에딧텍스트뷰 활성화
//                etvName.visibility = View.VISIBLE
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
//                etvName.visibility = View.INVISIBLE
                etvParty.visibility = View.INVISIBLE
                etvPhone.visibility = View.INVISIBLE

                // 텍스트뷰 초기화 및 활성화
//                tvName.text = etvName.text
                tvName.visibility = View.VISIBLE
                tvParty.text = etvParty.text
                tvParty.visibility = View.VISIBLE
                tvPhone.text = etvPhone.text
                tvPhone.visibility = View.VISIBLE

                // 버튼 이름 변경
                btnRewriteProfile.text = "프로필 수정하기"

                // 객체 값 변경
//                myAccount?.userName = etvName.text.toString()
                myAccount?.affiliated = etvParty.text.toString()
                myAccount?.phoneNumber = etvPhone.text.toString()

                // 객체 리스트 변경(테스트중)
//                try{
//                    val chkNullAcc: ContactData = myAccount ?: throw NullPointerException("myAccount 값 null 확인됨")
//                    ContactObject.rewriteContactList(myAccount.userName,myAccount.affiliated,myAccount.phoneNumber) }
//                catch (e : Exception){ Log.d("MyPageFragment", "수정된 프로필 리스트 반영 실패") }

                Log.d("MyPageFragment", "프로필 수정완료 버튼 로직 종료")
            }

            Log.d("MyPageFragment", "프로필 수정버튼 동작 종료")
        }

        Log.d("MyPageFragment", "onCreateView 상태 종료")

        // 뒤로가기 기능 추가
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("MyPageFragment", "뒤로가기 버튼 눌림")

                if(chkMode == true){
                    // 모드 변환
                    chkMode = false

                    // 텍스트뷰 활성화
                    tvParty.visibility = View.VISIBLE
                    tvPhone.visibility = View.VISIBLE

                    // 에디트뷰 비활성화
                    etvParty.setText(myAccount?.affiliated)
                    etvParty.visibility = View.INVISIBLE
                    etvPhone.setText(myAccount?.phoneNumber)
                    etvPhone.visibility = View.INVISIBLE

                    // 버튼 이름 변경
                    btnRewriteProfile.text = "프로필 수정하기"

                    // 모션 레이아웃 상태 고정
                    motAnchor.transitionToStart()

                    Log.d("MyPageFragment", "이거 왜 안되??")
                }
                else{
                    (parentFragment as MainFragment).requireView().findViewById<ViewPager2>(R.id.main_vp).currentItem = 0
                }

            }
        })

        return MyPageStdBinding.root
    }

    override fun onResume() {
        super.onResume()

        Log.d("MyPageFragment", "onResume 상태 시작")

        // 버튼용 변수 추가(박정호)
        val btnRewriteProfile:Button = MyPageStdBinding.mypageBtnFixProfile

        // 텍스트뷰 변수 추가 및 초기화(박정호)
        val tvName:TextView = MyPageStdBinding.mypageTextName
        tvName.text = myAccount?.userName
        val tvParty:TextView = MyPageStdBinding.mypageTextCompany
        tvParty.text = myAccount?.affiliated
        val tvPhone:TextView = MyPageStdBinding.mypageTextContentsPhone
        tvPhone.text = myAccount?.phoneNumber

        // 에딧텍스트뷰 변수 추가 및 초기화(박정호)
//        val etvName:EditText = MyPageStdBinding.mypageEdittextName
//        etvName.setText(myAccount?.userName)
        val etvParty:EditText = MyPageStdBinding.mypageEdittextCompany
        etvParty.setText(myAccount?.affiliated)
        val etvPhone:EditText = MyPageStdBinding.mypageEdittextContentsPhone
        etvPhone.setText(myAccount?.phoneNumber)

        if(chkMode == true){
            // 모드 변환
            chkMode = false

            // 텍스트뷰 활성화
            tvParty.visibility = View.VISIBLE
            tvPhone.visibility = View.VISIBLE

            // 에디트뷰 비활성화
            etvParty.setText(myAccount?.affiliated)
            etvParty.visibility = View.INVISIBLE
            etvPhone.setText(myAccount?.phoneNumber)
            etvPhone.visibility = View.INVISIBLE

            // 버튼 이름 변경
            btnRewriteProfile.text = "프로필 수정하기"

            Log.d("MyPageFragment", "이거 왜 안되2??")
        }

        MyPageStdBinding = MypageMainLayoutV1Binding.inflate(layoutInflater)

        // 레이아웃 최신화
        MyPageStdBinding.root.invalidate()

        // 뒤로가기 기능 추가
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("MyPageFragment", "뒤로가기 버튼 눌림")

                (parentFragment as MainFragment).requireView().findViewById<ViewPager2>(R.id.main_vp).currentItem = 0

            }
        })

        Log.d("MyPageFragment", "onResume 상태 종료")
    }
}
