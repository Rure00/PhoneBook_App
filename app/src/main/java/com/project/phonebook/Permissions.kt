package com.project.phonebook

import android.Manifest

object Permissions {
    val CONTACT_PERMISSION = arrayOf(
        Manifest.permission.READ_CONTACTS
    )
    const val CONTACT_REQUEST_CODE = 100
}