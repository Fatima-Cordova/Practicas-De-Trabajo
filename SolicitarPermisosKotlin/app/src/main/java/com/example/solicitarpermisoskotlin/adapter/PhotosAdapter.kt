package com.example.solicitarpermisoskotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solicitarpermisoskotlin.R
import com.example.solicitarpermisoskotlin.retrofit.model.PhotosResponse
import com.squareup.picasso.Picasso

class PhotosAdapter (private val listPhotos : List<PhotosResponse>, private val context: Context) :
    RecyclerView.Adapter<PhotosAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_photos, parent, false))
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var txtTitlePhotos: TextView = itemView.findViewById(R.id.txtTitlePhotos)
        var imgPhoto : ImageView = itemView.findViewById(R.id.imageView2)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photosAdapter = listPhotos.get(position)

        holder.txtTitlePhotos.text= photosAdapter.title
        Picasso
            .get()
            .load(photosAdapter.thumbnailUrl)
            .into(holder.imgPhoto)
    }

    override fun getItemCount(): Int {
        return listPhotos.size
    }

}