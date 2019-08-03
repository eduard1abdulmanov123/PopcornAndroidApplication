package com.abdulmanov.myapplication.ui.details

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.abdulmanov.myapplication.R
import com.abdulmanov.myapplication.common.inflate
import com.abdulmanov.myapplication.common.loadImg
import com.abdulmanov.myapplication.model.vo.Credit
import kotlinx.android.synthetic.main.credit_item.view.*

class CreditsAdapter: RecyclerView.Adapter<CreditsAdapter.CreditViewHolder>() {

    private val credits = mutableListOf<Credit>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CreditViewHolder {
        return CreditViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return credits.size
    }

    override fun onBindViewHolder(viewHolder: CreditViewHolder, position: Int) {
        viewHolder.bind(credits[position])
    }

    fun add(data:List<Credit>){
        credits.addAll(data)
    }
    inner class CreditViewHolder(parent:ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.credit_item)){
        fun bind(credit:Credit){
            with(itemView){
                credit_name.text = credit.name
                credit_job.text = credit.job
                credit_profile_image.loadImg(credit.profilePath,R.drawable.person)
            }
        }
    }

}