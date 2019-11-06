package com.abdulmanov.MoviCorn.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.domain.models.movies.Credit
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.item_list_credit.view.*
import java.lang.Exception

class CreditsAdapter(private val clickListener: (id:Long)->Unit): RecyclerView.Adapter<CreditsAdapter.CreditViewHolder>() {

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
        notifyDataSetChanged()
    }

    inner class CreditViewHolder(parent:ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_credit)){

        init {
            itemView.setOnClickListener {
                clickListener.invoke(credits[adapterPosition].id)
            }
        }

        fun bind(credit: Credit){
            with(itemView){
                credit_name.text = credit.name
                credit_job.text = credit.job
                if(credit.profilePath!=null) {
                    credit_profile_image.loadImg(
                        credit.profilePath,
                        R.color.color_background_image,
                        callback = object : Callback {
                            override fun onSuccess() {}

                            override fun onError(e: Exception?) {
                                credit_profile_image.loadImg(R.drawable.error_loading_image)
                            }

                        }
                    )
                }
                else {
                    credit_profile_image.loadImg(R.drawable.not_profile_image)
                    credit_profile_image.setBackgroundResource(R.drawable.placeholder_image)
                }
            }
        }
    }

}