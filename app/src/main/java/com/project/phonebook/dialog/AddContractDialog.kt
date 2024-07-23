package com.project.phonebook.dialog

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.project.phonebook.R
import com.project.phonebook.data.ContractData
import com.project.phonebook.data.`object`.ContractObject
import com.project.phonebook.databinding.DialogAddContractBinding

class AddContractDialog(private val onAcceptClick: (ContractData) -> Unit): DialogFragment() {
    private lateinit var binding: DialogAddContractBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddContractBinding.inflate(inflater)
        val userNameEditText = binding.contractDialogEtUserName
        val phoneNumEditText = binding.contractDialogEtPhoneNumber
        val affiliatedEditText = binding.contractDialogEtAffiliated
        val sendNotificationEditText = binding.contractDialogEtSendNotificationTime

        phoneNumEditText.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        var selectTimeValueSecond = 1
        binding.initSpinner()
        binding.contractDialogSpinnerSelectTimeType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    when (position) {
                        0 -> selectTimeValueSecond = 1
                        1 -> selectTimeValueSecond = 60
                        2 -> selectTimeValueSecond = 3600
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val currentListSize = ContractObject.getContractListSize()
        binding.contractDialogBtnAccept.setOnClickListener {
            if (userNameEditText.text.isEmpty() || phoneNumEditText.text.isEmpty() ||
                affiliatedEditText.text.isEmpty() || sendNotificationEditText.text.isEmpty()) {
                Toast.makeText(context, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phoneNumEditText.text.toString().length < 13) {
                Toast.makeText(context, "전화번호 13자리를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val addContractData = ContractData(
                id = currentListSize,
                profile = R.drawable.ic_account_circle,
                userName = userNameEditText.text.toString(),
                phoneNumber = phoneNumEditText.text.toString(),
                affiliated = affiliatedEditText.text.toString(),
                sendNotificationSec = sendNotificationEditText.text.toString().toInt() * selectTimeValueSecond,
                isFavorite = false
            )
            onAcceptClick(addContractData)

            dismiss()
        }

        binding.contractDialogBtnCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun DialogAddContractBinding.initSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.time_types)
        )
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        contractDialogSpinnerSelectTimeType.adapter = adapter
    }
}
