package com.aman.chatapp

interface DialogInterface {
    fun dialogOk(userType: Int, name: String, refId: String?)
    fun dialogCancel()
    interface ChatClicked {
        fun onChatItemClicked(name: String)

    }
}