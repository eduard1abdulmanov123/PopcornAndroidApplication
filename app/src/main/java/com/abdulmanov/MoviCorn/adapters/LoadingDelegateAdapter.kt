package com.abdulmanov.MoviCorn.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.abdulmanov.customviews.recyclerview.IDelegateAdapter
import com.abdulmanov.customviews.recyclerview.ItemView
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate

class LoadingDelegateAdapter : IDelegateAdapter<LoadingDelegateAdapter.LoadingViewHolder, LoadingDelegateAdapter.Loading> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LoadingViewHolder(parent)
    }

    override fun isForViewType(item: ItemView): Boolean {
        return item is Loading
    }

    class LoadingViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_loading))

    class Loading : ItemView
}