package com.example.hora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hora.MainActivity
import com.example.hora.R
import com.example.hora.model.MessageData
import com.example.hora.util.Time


class AdapterRecycler(val context: Context, val listMessages: ArrayList<MessageData>):
    RecyclerView.Adapter<AdapterRecycler.MessageViewHolder>() {

    private val dateFormat: Time = Time()

    inner class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var textMessage: TextView
        var txtTime: TextView
        private lateinit var messageData: MessageData

        init {
            textMessage = view.findViewById<TextView>(R.id.txtMessage)
            txtTime = view.findViewById<TextView>(R.id.txtTime)
        }

        fun bind(message: MessageData) {
            this.messageData = message
            textMessage.text = message.messageT
            txtTime.text = dateFormat.dateTime(message.createDate)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_message, parent, false)
        return MessageViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(listMessages[position])
    }

    override fun getItemCount(): Int {
        return listMessages.size
    }


}