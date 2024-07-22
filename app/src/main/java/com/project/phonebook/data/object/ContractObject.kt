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
                affiliated = "월드오브워쉽",
                isFavorite = false
            )
        )
        addContract(
            ContractData(
                id = 1,
                profile = R.drawable.ic_account_circle,
                userName = "김대현",
                affiliated = "마인크래프트",
                isFavorite = false
            )
        )
        addContract(
            ContractData(
                id = 2,
                profile = R.drawable.ic_account_circle,
                userName = "성승모",
                affiliated = "월드오브워쉽",
                isFavorite = false
            )
        )
        addContract(
            ContractData(
                id = 3,
                profile = R.drawable.ic_account_circle,
                userName = "임가람",
                affiliated = "마인크래프트",
                isFavorite = false
            )
        )
    }

    fun getContractList() = contractList

    private fun addContract(contractData: ContractData) {
        contractList.add(contractData)
    }
}