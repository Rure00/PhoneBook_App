package com.project.phonebook.dialog

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.project.phonebook.Permissions
import com.project.phonebook.R
import com.project.phonebook.data.ContactData
import com.project.phonebook.data.`object`.ContactObject
import com.project.phonebook.databinding.FragmentLoadContactDialogBinding
import java.util.regex.Pattern


class LoadContactDialog(private val dismissListener: DismissListener) : DialogFragment() {
    private var _biding: FragmentLoadContactDialogBinding? = null
    private val binding get() = _biding!!

    private val loadedContractList = mutableListOf<ContactData>()

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("LoadContactDialog", "Permission is Granted.")
                getContacts()
                dismiss()
            } else {
                Toast.makeText(requireContext(), getString(R.string.read_contact_reject_msg), Toast.LENGTH_SHORT).show()
                dismiss()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _biding = FragmentLoadContactDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadDialogRejectBtn.setOnClickListener {
            dismiss()
        }
        binding.loadDialogConfirmBtn.setOnClickListener {
            if(requireActivity().checkSelfPermission(Permissions.CONTACT_PERMISSION[0]) == PERMISSION_DENIED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            } else {
                Log.d("LoadContactDialog", "Permission is already Granted.")
                getContacts()
                dismiss()
            }
        }
    }

    private fun getContacts() {
        val resolver = requireContext().contentResolver
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        val projection =
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )

        val sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC"
        val cursor = resolver.query(uri, projection, null, null, sortOrder)

        cursor?.let {
            //TODO: 가능한 ContractListAdapter.items.size 받아와 초기화!
            val sizeContract = ContactObject.getContactListSize()
            var num = 0
            while(cursor.moveToNext()) {
                val nameIndex = cursor.getColumnIndex(projection[0])
                val numberIndex = cursor.getColumnIndex(projection[1])

                val name = cursor.getString(nameIndex)
                var number = cursor.getString(numberIndex)

                val pattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"
                if(!Pattern.matches(pattern, number)) {
                    number = "${number.take(3)}-${number.slice(3..6)}-${number.drop(7)}"
                }

                Log.d("LoadContactDialog", "$num) name $name, number: $number")

                loadedContractList.add(
                    ContactData(
                        id = sizeContract+num,
                        profile = R.drawable.ic_account_circle,
                        userName = name,
                        phoneNumber = number,
                        affiliated = "",
                        sendNotificationSec = 10,
                        isFavorite = false
                    )
                )

                num++
            }

        }

        cursor?.close()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener.onDismiss(loadedContractList)
    }

    interface DismissListener {
        fun onDismiss(list: List<ContactData>)
    }
}