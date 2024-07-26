package com.project.phonebook.data.`object`

import com.project.phonebook.data.NotificationData
import com.project.phonebook.data.NotificationMessageData

object MessageObject {
    private val messageList = mutableListOf<NotificationData>()

    fun getInstance() = messageList

    // sender: 메시지를 보낸 사람.
    // receiver: 메시지를 받을 사람.
    // message: 보낸 메시지
    fun addMessage(sender: String, receiver: String, message: String) {
        if (messageList.none { it.receiver == receiver }) {
            messageList.add(
                NotificationData(
                    receiver = receiver,
                    messageData = mutableListOf(NotificationMessageData(sender, message))
                )
            )
        } else {
            val receiverData = messageList.first { it.receiver == receiver }
            receiverData.messageData.add(
                NotificationMessageData(sender, message)
            )
        }
    }

    // 새로 로그인한 사람이
    fun checkMyMessage(userName: String): MutableList<NotificationMessageData> {
        val contains = messageList.filter { it.receiver == userName }
        return if (contains.isNotEmpty()) contains.first().messageData
        else mutableListOf()
    }
}