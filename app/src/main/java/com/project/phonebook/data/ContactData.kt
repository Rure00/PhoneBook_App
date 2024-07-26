package com.project.phonebook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactData(
    val id: Int,
    val profile: Int = 0,
    val userName: String = "",
    val phoneNumber: String = "",
    val affiliated: String = "",
    val sendNotificationSec: Int = 0,
    var isFavorite: Boolean = false,
): Parcelable
