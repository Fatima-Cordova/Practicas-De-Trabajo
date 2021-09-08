package com.example.solicitarpermisoskotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solicitarpermisoskotlin.R
import com.example.solicitarpermisoskotlin.retrofit.model.MessageResponse

class AdaptadorRecycler (private val newsList: ArrayList<MessageResponse>) :
    RecyclerView.Adapter<AdaptadorRecycler.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_items, parent, false)
        return MyViewHolder(itemView)
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txtDatosRecycler: TextView = itemView.findViewById(R.id.txtList)

        fun bind(messageResponse: MessageResponse) {
            txtDatosRecycler.text = messageResponse.title
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

}