package com.aman.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aman.chatapp.FirebaseChat
import com.aman.chatapp.R
import com.aman.chatapp.UserType
import kotlinx.android.synthetic.main.my_message.view.*
import kotlinx.android.synthetic.main.other_message.view.*


private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class MessageAdapter (val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {
    private val messages: ArrayList<FirebaseChat> = ArrayList()

    fun addMessage(message: FirebaseChat){
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        if(message.type!!.equals(UserType.ADMIN.toString(), ignoreCase = true) || message.type!!.equals(UserType.ASTROLOGER.toString(), ignoreCase = true)){
            return  VIEW_TYPE_OTHER_MESSAGE

        }else
            return VIEW_TYPE_MY_MESSAGE

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.my_message, parent, false))
        } else {
            OtherMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.other_message, parent, false))
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)

        holder?.bind(message)
    }

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage
        private var timeText: TextView = view.txtMyMessageTime

        override fun bind(message: FirebaseChat) {
            messageText.text = message.msg
//            timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }
    }

    inner class OtherMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtOtherMessage
        private var userText: TextView = view.txtOtherUser
        private var timeText: TextView = view.txtOtherMessageTime

        override fun bind(message: FirebaseChat) {
            messageText.text = message.msg
            if(message.type!!.equals(UserType.ASTROLOGER.toString(), ignoreCase = true))
                userText.text = message.type +" "+ message.name
            else
                userText.text = message.type
            timeText.text = message.date
        }
    }
}

open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message:FirebaseChat) {}
}
