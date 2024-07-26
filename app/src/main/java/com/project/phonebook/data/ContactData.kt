package com.project.phonebook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactData(
    val id: Int,
    var profile: Int = 0,
    var userName: String = "",
    var phoneNumber: String = "",
    var affiliated: String = "",
    var sendNotificationSec: Int = 0,
    var isFavorite: Boolean = false
): Parcelable
