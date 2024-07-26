package com.project.phonebook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.phonebook.R
import com.project.phonebook.data.`object`.MyPageAccinfo
import com.project.phonebook.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _biding: FragmentLoginBinding? = null
    private val binding get() = _biding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _biding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            loginJhBtn.setOnClickListener {
                MyPageAccinfo.accName = "박정호"
                MyPageAccinfo.accPhone = "010-1111-1111"
                MyPageAccinfo.accParty = "마인크래프트"

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fcv, MainFragment()).commitNow()
            }
            loginGrBtn.setOnClickListener {
                MyPageAccinfo.accName = "임가람"
                MyPageAccinfo.accPhone = "010-2222-2222"
                MyPageAccinfo.accParty = "월드오브워쉽"

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fcv, MainFragment()).commitNow()
            }
            loginJhBtn.setOnClickListener {
                MyPageAccinfo.accName = "성승모"
                MyPageAccinfo.accPhone = "010-3333-3333"
                MyPageAccinfo.accParty = "스타듀벨리"

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fcv, MainFragment()).commitNow()
            }
            loginJhBtn.setOnClickListener {
                MyPageAccinfo.accName = "김대현"
                MyPageAccinfo.accPhone = "010-4444-4444"
                MyPageAccinfo.accParty = "로스트아크"

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fcv, MainFragment()).commitNow()
            }
        }
    }
}