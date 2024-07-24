package com.project.phonebook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailTitleData(
    val title: String
):Parcelable
