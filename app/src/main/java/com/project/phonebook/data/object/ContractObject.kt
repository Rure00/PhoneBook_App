package com.project.phonebook.data.`object`

import com.project.phonebook.R
import com.project.phonebook.data.ContractData

object ContractObject {
    private val contractList = mutableListOf<ContractData>()

    init {
        addContract(
            ContractData(
                id = 0,
                profile = R.drawable.ic_account_circle,
                userName = "박정호",
                phoneNumber = "010-1234-5678",
                affiliated = "월드오브워쉽",
                sendNotificationSec = 10,
                isFavorite = false
            )
        )
        addContract(
            ContractData(
                id = 1,
                profile = R.drawable.ic_account_circle,
                userName = "김대현",
                phoneNumber = "010-2345-6789",
                affiliated = "마인크래프트",
                sendNotificationSec = 5,
                isFavorite = false
            )
        )
        addContract(
            ContractData(
                id = 2,
                profile = R.drawable.ic_account_circle,
                userName = "성승모",
                phoneNumber = "010-3456-7890",
                affiliated = "월드오브워쉽",
                sendNotificationSec = 1,
                isFavorite = false
            )
        )
        addContract(
            ContractData(
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

    fun getContractList() = contractList

    fun getContractListSize() = contractList.size

    private fun addContract(contractData: ContractData) {
        contractList.add(contractData)
    }
}