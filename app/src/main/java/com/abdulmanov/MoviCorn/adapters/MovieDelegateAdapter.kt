package com.abdulmanov.MoviCorn.adapters

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import com.abdulmanov.customviews.recyclerview.IDelegateAdapter
import com.abdulmanov.customviews.recyclerview.ItemView
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.MoviCorn.model.Movie

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_little_poster.view.*
import java.text.FieldPosition


class MovieDelegateAdapter(
    private val clickListener: (id:Long)->Unit,
    private val longClickListener: ((position:Int)->Unit)?=null
) : IDelegateAdapter<MovieDelegateAdapter.MovieViewHolder, Movie> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, item: Movie) {
        holder.bind(item)
    }

    override fun isForViewType(item: ItemView) = item is Movie

    override fun onRecycled(holder: MovieViewHolder) {
        super.onRecycled(holder)
        holder.remove()
    }

    inner class MovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_little_poster)) {

        private val monthArray =  itemView.context.resources.getStringArray(R.array.month)

        init {
            with(itemView){
                setOnLongClickListener {
                    longClickListener?.invoke(adapterPosition)
                    return@setOnLongClickListener true
                }
            }
        }

        fun bind(item: Movie) {
            with(itemView) {
                setOnClickListener { clickListener.invoke(item.id) }
                little_movie_poster.loadImg(item.posterPath,R.drawable.rectangle_gray_transparent)
                if(!item.releaseDate.isNullOrEmpty()) {
                    little_movie_release.visibility = View.VISIBLE
                    little_movie_release.text = getDate(item.releaseDate)
                }
                little_movie_name.text = item.title
                little_movie_vote_average.text = item.voteAverage
                little_movie_vote_count.text = item.voteCount
                if(item.genres.isNotEmpty()) {
                    little_container_for_genres.visibility = View.VISIBLE
                    little_container_for_genres.text = item.genres.joinToString(", ")
                }
            }
        }

        fun remove(){
            with(itemView) {
                Picasso.get().cancelRequest(little_movie_poster)
                little_movie_poster.setImageDrawable(null)
            }
        }

        private fun getDate(releaseDate:String?):String{
            Log.d("LibraryTesting",releaseDate.toString())
            return releaseDate?.let {
                val date = it.split("-")
                "${date[2].toInt()} ${monthArray[date[1].toInt()-1]} ${date[0]}"
            } ?: ""
        }
    }
}