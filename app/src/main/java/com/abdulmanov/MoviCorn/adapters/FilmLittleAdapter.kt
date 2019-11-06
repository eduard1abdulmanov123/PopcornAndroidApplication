package com.abdulmanov.MoviCorn.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.domain.models.movies.MovieShort
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.item_list_film_little.view.*
import java.lang.Exception

class FilmLittleAdapter(private val clickListener: (id:Long)->Unit):RecyclerView.Adapter<FilmLittleAdapter.FilmLittleHolder>(){

    private val movies = mutableListOf<MovieShort>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmLittleHolder {
        return FilmLittleHolder(parent)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: FilmLittleHolder, position: Int) {
       holder.bind(movies[position])
    }

    fun add(data:List<MovieShort>){
        movies.addAll(data)
        notifyDataSetChanged()
    }

    inner class FilmLittleHolder(parent: ViewGroup):RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_film_little)){
        init {
            itemView.setOnClickListener {
                clickListener.invoke(movies[adapterPosition].id)
            }
        }

        fun bind(movieShort: MovieShort){
            with(itemView){
                if(movieShort.posterPath!=null) {
                    film_little_poster.loadImg(
                        movieShort.posterPath,
                        R.color.color_background_image,
                        callback = object : Callback {
                            override fun onSuccess() {}

                            override fun onError(e: Exception?) {
                                film_little_poster.loadImg(R.drawable.error_loading_image)
                            }

                        }
                    )
                }else{
                    film_little_poster.loadImg(R.drawable.not_poster_image)
                }
                film_little_name.text = movieShort.title
                film_little_release_data.text = movieShort.releaseDate ?: ""
                film_little_vote_average.text = movieShort.voteAverage
            }
        }
    }
}