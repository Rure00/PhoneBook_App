package com.project.phonebook.data.`object`

import com.project.phonebook.R
import com.project.phonebook.data.ContactData

object ContactObject {
    private val contactList = mutableListOf<ContactData>()

    init {
        addContact(
            ContactData(
                id = 0,
                profile = R.drawable.ic_account_circle,
                userName = "박정호",
                phoneNumber = "010-1234-5678",
                affiliated = "월드오브워쉽",
                sendNotificationSec = 10,
                isFavorite = true
            )
        )
        addContact(
            ContactData(
                id = 1,
                profile = R.drawable.ic_account_circle,
                userName = "김대현",
                phoneNumber = "010-2345-6789",
                affiliated = "마인크래프트",
                sendNotificationSec = 5,
                isFavorite = false
            )
        )
        addContact(
            ContactData(
                id = 2,
                profile = R.drawable.ic_account_circle,
                userName = "성승모",
                phoneNumber = "010-3456-7890",
                affiliated = "월드오브워쉽",
                sendNotificationSec = 1,
                isFavorite = true
            )
        )
        addContact(
            ContactData(
                id = 3,
                profile = R.drawable.ic_account_circle,
                userName = "임가람",
                phoneNumber = "010-4567-8901",
                affiliated = "마인크래프트",
                sendNotificationSec = 0,
                isFavorite = false
            )
        )
    }

    fun getContactList() = contactList

    fun getContactListSize() = contactList.size

    private fun addContact(contactData: ContactData) {
        contactList.add(contactData)
    }
}