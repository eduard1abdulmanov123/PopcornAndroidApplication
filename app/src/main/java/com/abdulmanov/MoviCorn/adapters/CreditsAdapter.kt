package com.abdulmanov.MoviCorn.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.MoviCorn.model.vo.movie.MovieCredit
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.activity_details_movie.*
import kotlinx.android.synthetic.main.item_list_credit.view.*
import java.lang.Exception

class CreditsAdapter(private val clickListener: (id:Long)->Unit): RecyclerView.Adapter<CreditsAdapter.CreditViewHolder>() {

    private val credits = mutableListOf<MovieCredit>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CreditViewHolder {
        return CreditViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return credits.size
    }

    override fun onBindViewHolder(viewHolder: CreditViewHolder, position: Int) {
        viewHolder.bind(credits[position])
    }

    fun add(data:List<MovieCredit>){
        credits.addAll(data)
        notifyDataSetChanged()
    }

    inner class CreditViewHolder(parent:ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_credit)){

        init {
            itemView.setOnClickListener {
                clickListener.invoke(credits[adapterPosition].id)
            }
        }

        fun bind(movieCredit: MovieCredit){
            with(itemView){
                credit_name.text = movieCredit.name
                credit_job.text = movieCredit.job
                if(movieCredit.profilePath!=null) {
                    credit_profile_image.loadImg(
                        movieCredit.profilePath,
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