package com.project.phonebook.data

data class NotificationData(
    val receiver: String = "",
    val messageData: MutableList<NotificationMessageData> = mutableListOf()
)

data class NotificationMessageData(
    val sender: String = "",
    val message: String = ""
)
