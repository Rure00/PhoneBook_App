package com.project.phonebook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContractData(
    val id: Int,
    val profile: Int = 0,
    val userName: String = "",
    val affiliated: String = "",
    var isFavorite: Boolean = false
): Parcelable