package com.project.phonebook.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.phonebook.data.ContactData
import com.project.phonebook.adapter.ContactDetailAdapter
import com.project.phonebook.data.DetailTitleData
import com.project.phonebook.databinding.FragmentContactDetailBinding

private const val ARG_PARAM1 = "param1"

interface FragmentMessageDataListener {
    fun onMessageDataReceived(messageOn: Boolean)
}

interface FragmentCallDataListener {
    fun onCallDataReceived(callOn: Boolean)
}


class ContactDetailFragment(private val contactData: ContactData) : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding
    private lateinit var adapter: ContactDetailAdapter

    val SEND_REQUEST_CODE = 1
    val CALL_REQUEST_CODE = 1

    //Fragment -> main
    private var messageListener: FragmentMessageDataListener? = null
    private var callListener: FragmentCallDataListener? = null
    private var param1: String? = null
    private val _binding get() = binding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentMessageDataListener) {
            messageListener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }

        if (context is FragmentCallDataListener) {
            callListener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

        /*뒤로가기*/
        requireActivity().onBackPressed {
            requireActivity().supportFragmentManager
                .beginTransaction().remove(this).commitNow()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smsClickListener()
        callClickListener()
    }

    private fun smsClickListener() {
        val btnmessage = binding.detailBtnMessage
        val messageTelNum = "010-1234-5678" //추후 데이터 받아서 넣을예정
        btnmessage.setOnClickListener {
            var callMessage = false
            var telNum = messageTelNum
            val permissionCheck = ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.SEND_SMS
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.SEND_SMS)) {
                    Toast.makeText(requireContext(), "권한이 필요합니다", Toast.LENGTH_SHORT).show()
                }
                requestPermissions(arrayOf(android.Manifest.permission.SEND_SMS), SEND_REQUEST_CODE)
            } else {
                callMessage = true
                messageListener?.onMessageDataReceived(callMessage)
            }
        }
    }

    private fun callClickListener() {
        val btnCall = binding.detailBtnCall
        val permissionCheck =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        btnCall.setOnClickListener {
            var callDial = false
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(requireContext(), "권한이 필요합니다", Toast.LENGTH_SHORT).show()
                }
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), CALL_REQUEST_CODE)
            } else {
                callDial = true
                callListener?.onCallDataReceived(callDial)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater)

        val arg = arguments?.getParcelable("contact", ContactData::class.java)
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
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callback()
                }
            }
        )
    }
}