package com.aman.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterChatList(
    val context:Context,
    private val nodes: ArrayList<String> = ArrayList(),
    private val selectedNode: DialogInterface.ChatClicked
):
    RecyclerView.Adapter<RecyclerAdapterChatList.ViewHolder>() {

    class ViewHolder(val mView: View): RecyclerView.ViewHolder(mView) {
        val tvZodiacName: TextView = mView.findViewById(R.id.tvText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_chat_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nodes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvZodiacName.text = nodes[position]
        holder.itemView.setOnClickListener {
            selectedNode.onChatItemClicked(nodes[position])
        }
    }

}