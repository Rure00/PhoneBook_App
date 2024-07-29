package com.project.phonebook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactData(
    val id: Int,
    var profile: Int = 0,
    val userName: String = "",
    var phoneNumber: String = "",
    var affiliated: String = "",
    var isFavorite: Boolean = false
): Parcelable
