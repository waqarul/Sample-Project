package com.waqar.sampleproject.features.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.waqar.sampleproject.R
import java.util.*

class ImageRecycledPagerAdapter() :
    RecycledPagerAdapter<RecyclerView.ViewHolder>() {

    private var viewItems: List<String> = ArrayList()

    fun setViewItems(viewItems: List<String>?) {
        viewItems?.let {
            this.viewItems = it
            notifyDataSetChanged()
        } ?: run {
            clear()
        }
    }

    private fun clear() {
        this.viewItems = ArrayList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        // Inflate view
        val view =
            LayoutInflater.from(parent!!.context).inflate(R.layout.image_view_item, parent, false)
        // Return view holder
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val vHolder = (viewHolder as ImageViewHolder)
        vHolder.bindData(viewItems[position])
    }

    override fun getCount(): Int {
        return viewItems.size
    }

    private class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.iv_vehicle)

        fun bindData(imageUrl: String) {
            Glide
                .with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView)
        }
    }
}