package com.abdulmanov.customviews.recyclerview

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup

interface IDelegateAdapter<in VH : RecyclerView.ViewHolder, in T : ItemView> {

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: VH, item: T){}

    fun onRecycled(holder: VH) {}

    fun isForViewType(item: ItemView): Boolean
}