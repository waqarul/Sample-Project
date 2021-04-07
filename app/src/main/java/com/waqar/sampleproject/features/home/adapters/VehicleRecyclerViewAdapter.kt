package com.waqar.sampleproject.features.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.afollestad.viewpagerdots.DotsIndicator
import com.waqar.sampleproject.R
import com.waqar.sampleproject.base.viewitem.BaseViewItem
import com.waqar.sampleproject.features.home.viewitems.AdvertisementViewItem
import com.waqar.sampleproject.features.home.viewitems.VehicleViewItem
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.set

class VehicleRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var viewItems: List<BaseViewItem> = ArrayList()
    private val viewPageStates = HashMap<Int, Int>()

    fun setViewItems(viewItems: List<BaseViewItem>?) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.vehicle_view_item -> VehicleViewHolder(view)
            R.layout.advertisement_view_item -> AdvertisementViewHolder(view)
            else -> throw IllegalArgumentException("Unhandled view type in onCreateViewHolder VehicleRecyclerViewAdapter.")
        }
    }

    override fun getItemCount(): Int {
        return viewItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (viewItems[position]) {
            is VehicleViewItem -> R.layout.vehicle_view_item
            is AdvertisementViewItem -> R.layout.advertisement_view_item
            else -> throw IllegalArgumentException("Unhandled view type in getItemViewType VehicleRecyclerViewAdapter.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val viewItem = viewItems[position]) {
            is VehicleViewItem -> {
                val viewHolder = (holder as VehicleViewHolder)
                viewHolder.bindData(viewItem, position, viewPageStates)
            }
            is AdvertisementViewItem -> {
                val viewHolder = (holder as AdvertisementViewHolder)
                viewHolder.bindData(viewItem)
            }
        }
    }

    private class VehicleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var title: TextView = view.findViewById(R.id.tv_title)
        private var price: TextView = view.findViewById(R.id.tv_price)
        private var fuel: TextView = view.findViewById(R.id.tv_fuel)
        private var pagerIndicator: DotsIndicator = view.findViewById(R.id.dots_indicator)
        var imageViewPager: ViewPager = view.findViewById(R.id.vp_vehicle_image)

        fun bindData(viewItem: VehicleViewItem, position: Int, viewPageStates: Map<Int, Int>) {
            title.text = viewItem.title
            price.text = HtmlCompat.fromHtml(viewItem.price, HtmlCompat.FROM_HTML_MODE_LEGACY)
            fuel.text = HtmlCompat.fromHtml(viewItem.fuel, HtmlCompat.FROM_HTML_MODE_LEGACY)

            val imageAdapter = ImageRecycledPagerAdapter()
            imageViewPager.adapter = imageAdapter
            imageAdapter.setViewItems(viewItem.vehicleImagesUrl)
            pagerIndicator.attachViewPager(imageViewPager)

            if (viewPageStates.containsKey(position)) {
                imageViewPager.currentItem = viewPageStates[position]!!
            }
        }
    }

    private class AdvertisementViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var advertisementImageView: ImageView = view.findViewById(R.id.iv_advertisement)

        fun bindData(viewItem: AdvertisementViewItem) {
            advertisementImageView.setImageResource(viewItem.advertisementImage)
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is VehicleViewHolder) {
            viewPageStates[holder.getAdapterPosition()] = holder.imageViewPager.currentItem
            super.onViewRecycled(holder)
        }
    }
}