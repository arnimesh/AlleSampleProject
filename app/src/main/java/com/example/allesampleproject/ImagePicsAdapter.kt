package com.example.allesampleproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImagePicsAdapter(private val imageList: List<ImagePics>, private val context: Context) : RecyclerView.Adapter<ImagePicsAdapter.FoodViewHolder>(){

    lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }
    class FoodViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val ImagePicView:ImageView=itemView.findViewById(R.id.imageView)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.image_container,parent,false)
        return FoodViewHolder(view,mListener)
    }

    override fun getItemCount(): Int {

        return imageList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int)
    {
        val currImage=imageList[position]

        Glide.with(context)
            .load(currImage.imagePics)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.one) // Placeholder image
                    .error(R.drawable.one) // Error image in case of loading failure
            )
            .into(holder.ImagePicView)

    }

}