package com.project.phonebook.dialog

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.project.phonebook.R
import com.project.phonebook.data.ContactData
import com.project.phonebook.data.`object`.ContactObject
import com.project.phonebook.databinding.DialogAddContactBinding

class AddContactDialog(private val onAcceptClick: (ContactData) -> Unit): DialogFragment() {
    private lateinit var binding: DialogAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddContactBinding.inflate(inflater)
        val userNameEditText = binding.contactDialogEtUserName
        val phoneNumEditText = binding.contactDialogEtPhoneNumber
        val affiliatedEditText = binding.contactDialogEtAffiliated

        phoneNumEditText.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val currentListSize = ContactObject.getContactListSize()
        binding.contactDialogBtnAccept.setOnClickListener {
            if (userNameEditText.text.isEmpty() || phoneNumEditText.text.isEmpty() || affiliatedEditText.text.isEmpty()) {
                Toast.makeText(context, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phoneNumEditText.text.toString().length < 13) {
                Toast.makeText(context, "전화번호 13자리를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val addContactData = ContactData(
                id = currentListSize,
                profile = R.drawable.ic_account_circle,
                userName = userNameEditText.text.toString(),
                phoneNumber = phoneNumEditText.text.toString(),
                affiliated = affiliatedEditText.text.toString(),
                isFavorite = false
            )
            onAcceptClick(addContactData)

            dismiss()
        }

        binding.contactDialogBtnCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}
