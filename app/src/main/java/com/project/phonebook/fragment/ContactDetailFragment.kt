package com.project.phonebook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.project.phonebook.R
import com.project.phonebook.databinding.FragmentContactDetailBinding


class ContactDetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        val title = mutableListOf<DetailContentTitleData>()
//        title.add(DetailContentTitleData("Phone Number", "Event", "E-Mail"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_detail,container,false)


    }


}