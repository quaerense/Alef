package org.quaerense.alef

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    var imageUrls = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onImageClickListener: OnImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.image_item, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Picasso.get()
            .load(imageUrls[position])
            .placeholder(R.drawable.image_not_found)
            .into(holder.ivImage)

        holder.itemView.setOnClickListener {
            onImageClickListener?.invoke(imageUrls[position])
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    interface OnImageClickListener {
        fun invoke(imageUrl: String)
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
    }
}