package com.project.phonebook.data.`object`

import com.project.phonebook.R
import com.project.phonebook.data.ContactData

object ContactObject {
    private val contactList = mutableListOf<ContactData>()

    init {
        addContact(
            ContactData(
                id = 0,
                profile = R.drawable.mypage_prf_img,
                userName = "박정호",
                phoneNumber = "010-1234-5678",
                affiliated = "월드오브워쉽",
                isFavorite = true
            )
        )
        addContact(
            ContactData(
                id = 1,
                profile = R.drawable.image_kdh,
                userName = "김대현",
                phoneNumber = "010-2345-6789",
                affiliated = "마인크래프트",
                isFavorite = false
            )
        )
        addContact(
            ContactData(
                id = 2,
                profile = R.drawable.image_ssm,
                userName = "성승모",
                phoneNumber = "010-3456-7890",
                affiliated = "월드오브워쉽",
                isFavorite = true
            )
        )
        addContact(
            ContactData(
                id = 3,
                profile = R.drawable.image_lgr,
                userName = "임가람",
                phoneNumber = "010-4567-8901",
                affiliated = "마인크래프트",
                isFavorite = false
            )
        )
    }

    fun getContactList() = contactList

    fun getContactListSize() = contactList.size

    // 프로필 수정용 메서드(테스트중)
//    fun rewriteContactList(tgtName: String, reAff:String, rePhone: String){
//        contactList.forEach { contactData ->
//            if(contactData.userName == tgtName){
//                contactData.affiliated = reAff
//                contactData.phoneNumber = rePhone
//                return
//            }
//        }
//    }

    private fun addContact(contactData: ContactData) {
        contactList.add(contactData)
    }
}