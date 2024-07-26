package com.project.phonebook.dialog

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.project.phonebook.data.`object`.MessageObject
import com.project.phonebook.databinding.DialogSendNotificationMessageBinding

class SendNotificationMessageDialog(private val sender: String, private val receiver: String): DialogFragment() {
    private lateinit var binding: DialogSendNotificationMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSendNotificationMessageBinding.inflate(inflater)
        Log.d("TAG", "onCreateView message data: ${MessageObject.getInstance()}")

        binding.dialogAddNotificationTvTitle.text = "${receiver}님에게 메시지 보내기"

        binding.dialogAddNotificationBtnSend.setOnClickListener {
            val currentEditTextValue = binding.dialogAddNotificationEtMessage.text.toString()

            if (currentEditTextValue.isEmpty()) {
                Toast.makeText(context, "보낼 메시지가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                MessageObject.addMessage(
                    sender, receiver,
                    message = binding.dialogAddNotificationEtMessage.text.toString()
                )
            }

            dismiss()
        }
        binding.dialogAddNotificationBtnCancel.setOnClickListener { dismiss() }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val windowMetrics = windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        val deviceWidth = windowMetrics.bounds.width() - insets.left - insets.right

        val params = dialog?.window?.attributes
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}