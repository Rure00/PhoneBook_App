package com.project.phonebook.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import com.project.phonebook.R
import com.project.phonebook.data.ContractData
import com.project.phonebook.databinding.FragmentContactDetailBinding


class ContactDetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        val title = mutableListOf<DetailContentTitleData>()
//        title.add(DetailContentTitleData("Phone Number", "Event", "E-Mail"))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arg = arguments?.getParcelable("contact", ContractData::class.java)
        Log.d("TAG", "onCreateView: $arg")

        return inflater.inflate(R.layout.fragment_contact_detail,container,false)


    }


}