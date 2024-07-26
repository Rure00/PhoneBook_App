package com.project.phonebook.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.phonebook.MainActivity
import com.project.phonebook.R
import com.project.phonebook.data.ContractData
import com.project.phonebook.adapter.ContactDetailAdapter
import com.project.phonebook.data.DetailTitleData
import com.project.phonebook.databinding.FragmentContactDetailBinding
import com.project.phonebook.fragment.ContractListFragment


class ContactDetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding
    private lateinit var adapter: ContactDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*뒤로가기 추가중*/
        requireActivity().onBackPressed {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fcv,
                ContractListFragment()
            ).commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater)

        val arg = arguments?.getParcelable("contact", ContractData::class.java)
        Log.d("TAG", "onCreateView: $arg")

        setRecyclerView()

        return binding.root

    }

    private fun setRecyclerView() {
        val titleList = mutableListOf<DetailTitleData>()
        titleList.add(DetailTitleData("Phone Number"))
        titleList.add(DetailTitleData("Event"))
        titleList.add(DetailTitleData("E-mail Address"))

        adapter = ContactDetailAdapter(titleList)
        binding.detailRecyclerview.adapter = adapter
        binding.detailRecyclerview.layoutManager = LinearLayoutManager(requireContext())


    }


    override fun onDestroyView() {
        super.onDestroyView()
    }


    /*뒤로가기버튼*/
    private fun FragmentActivity.onBackPressed(callback: () -> Unit) {
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callback()
                }
            }
        )
    }
}





