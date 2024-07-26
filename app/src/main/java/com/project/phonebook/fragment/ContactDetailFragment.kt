package com.project.phonebook.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.project.phonebook.R
import com.project.phonebook.data.ContactData
import com.project.phonebook.data.`object`.ContactObject
import com.project.phonebook.databinding.FragmentContactDetailBinding

private const val ARG_PARAM1 = "param1"

interface FragmentMessageDataListener {
    fun onMessageDataReceived(messageOn: Boolean, messageNum: String, messageTargetUser: String)
}

interface FragmentCallDataListener {
    fun onCallDataReceived(callOn: Boolean, callNum: String)
}


class ContactDetailFragment(private val contactData: ContactData) : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding
    private lateinit var callback: OnBackPressedCallback


    val SEND_REQUEST_CODE = 1
    val CALL_REQUEST_CODE = 1

    //Fragment -> main
    private var messageListener: FragmentMessageDataListener? = null
    private var callListener: FragmentCallDataListener? = null
    private var param1: String? = null


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

        Log.d("backpressed", "back")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smsClickListener()
        callClickListener()
    }

    private fun smsClickListener() {
        val btnmessage = binding.detailBtnMessage
        val messageTelNum = contactData.phoneNumber //추후 데이터 받아서 넣을예정
        btnmessage.setOnClickListener {
            var callMessage = false
            var telNum = messageTelNum
            var telTargetUser = contactData.userName
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
                messageListener?.onMessageDataReceived(callMessage, telNum, telTargetUser)
            }
        }
    }

    private fun callClickListener() {
        val btnCall = binding.detailBtnCall
        val callNum = contactData.phoneNumber
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
                callListener?.onCallDataReceived(callDial, callNum)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater)

        Log.d("TAG", "onCreateView: $contactData")
        //val arg = arguments?.getParcelable("contact", ContactData::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager
                    .beginTransaction().remove(this@ContactDetailFragment).commit()
            }
        })

        binding.detailIvProfile.setImageResource(R.drawable.ic_account_circle)
        binding.detailTvProfileId.text = contactData?.userName
        binding.detailFirstListContent.text = contactData.phoneNumber
        binding.detailSecondListContent.text = contactData.affiliated


        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}