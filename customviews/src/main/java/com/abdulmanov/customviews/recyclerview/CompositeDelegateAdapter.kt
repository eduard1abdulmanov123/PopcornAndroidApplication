package com.abdulmanov.customviews.recyclerview


import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.util.*

class CompositeDelegateAdapter private constructor(
    private val mDelegateAdapters: SparseArrayCompat<IDelegateAdapter<RecyclerView.ViewHolder, ItemView>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val FIRST_VIEW_TYPE = 0
    }

    private val mData: ArrayList<ItemView> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return mDelegateAdapters.get(viewType)!!.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mDelegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, mData[position])
        Log.d("Scrool","bind")
    }

    override fun getItemViewType(position: Int): Int {
        for (i in FIRST_VIEW_TYPE..mDelegateAdapters.size()) {
            val delegateAdapter = mDelegateAdapters.valueAt(i)
            if (delegateAdapter.isForViewType(mData[position])) {
                return mDelegateAdapters.keyAt(i)
            }
        }
        throw NullPointerException("can not find viewType for position $position")
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        mDelegateAdapters.get(holder.itemViewType)!!.onRecycled(holder)
    }

    fun swapData(data: List<ItemView>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: ItemView) {
        mData.add(data)
        notifyItemInserted(mData.size)
    }

    fun removeData(data: ItemView) {
        val index = mData.indexOf(data)
        if(index!=-1) {
            mData.remove(data)
            notifyItemRemoved(index)
        }
    }

    fun addAllData(data: List<ItemView>) {
        val initPosition = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(initPosition, mData.size)
    }

    class Builder {
        private var mCount = 0
        private val mDelegateAdapter: SparseArrayCompat<IDelegateAdapter<RecyclerView.ViewHolder, ItemView>> =
            SparseArrayCompat()

        @Suppress("UNCHECKED_CAST")
        fun <VH : RecyclerView.ViewHolder, IV : ItemView> add(delegateAdapter: IDelegateAdapter<VH, IV>): Builder {
            mDelegateAdapter.put(mCount++, delegateAdapter as? IDelegateAdapter<RecyclerView.ViewHolder, ItemView>)
            return this
        }

        fun build(): CompositeDelegateAdapter {
            if (mCount == 0) {
                throw IllegalArgumentException("Register at least one adapter")
            }
            return CompositeDelegateAdapter(mDelegateAdapter)
        }
    }
}