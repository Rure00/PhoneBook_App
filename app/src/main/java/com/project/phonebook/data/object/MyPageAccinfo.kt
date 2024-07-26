package com.project.phonebook.data.`object`

import com.project.phonebook.R
import kotlinx.parcelize.Parcelize

// MyPageRewrite >> MyPage 간 전달용 계정정보

object MyPageAccinfo {
    var accName:String? = null
    var accPhone:String? = null
    var accParty:String? = null
    var accChk:Boolean = false
}