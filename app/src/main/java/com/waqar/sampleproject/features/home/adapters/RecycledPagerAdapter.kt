package com.waqar.sampleproject.features.home.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import java.util.*


/**
 * See https://gist.github.com/tvkkpt/2e667cb707859123b422
 * @param <VH>
</VH> */
abstract class RecycledPagerAdapter<VH : RecyclerView.ViewHolder?> :
    PagerAdapter() {
    abstract class ViewHolder(val itemView: View)

    var destroyedItems: Queue<VH> = LinkedList()
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var viewHolder = destroyedItems.poll()
        if (viewHolder != null) {
            // Re-add existing view before rendering so that we can make change inside getView()
            container.addView(viewHolder.itemView)
            onBindViewHolder(viewHolder, position)
        } else {
            viewHolder = onCreateViewHolder(container)
            onBindViewHolder(viewHolder, position)
            container.addView(viewHolder!!.itemView)
        }
        return viewHolder
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView((`object` as VH)!!.itemView)
        destroyedItems.add(`object` as VH)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (`object` as VH)!!.itemView === view
    }

    /**
     * Create a new view holder
     * @param parent
     * @return view holder
     */
    abstract fun onCreateViewHolder(parent: ViewGroup?): VH

    /**
     * Bind data at position into viewHolder
     * @param viewHolder
     * @param position
     */
    abstract fun onBindViewHolder(viewHolder: VH, position: Int)
}
