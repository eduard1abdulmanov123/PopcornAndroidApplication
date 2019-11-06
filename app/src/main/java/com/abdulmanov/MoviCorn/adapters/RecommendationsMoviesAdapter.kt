package com.abdulmanov.MoviCorn.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.getScreenSize
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.domain.models.movies.MovieShort
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.item_list_film_little.view.*
import kotlinx.android.synthetic.main.item_list_recommendation_movie.view.*
import java.lang.Exception

class  RecommendationsMoviesAdapter(
    private val clickListener: (id:Long)->Unit
):RecyclerView.Adapter<RecommendationsMoviesAdapter.RecommendationMovieHolder>() {

    private val movies = mutableListOf<MovieShort>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecommendationMovieHolder(parent)

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecommendationMovieHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun add(data:List<MovieShort>){
        movies.addAll(data)
        notifyDataSetChanged()
    }

    inner class RecommendationMovieHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_recommendation_movie)) {

        init {
            itemView.setOnClickListener {
                clickListener.invoke(movies[adapterPosition].id)
            }

            with(itemView.layoutParams){
                val size = itemView.context.getScreenSize()
                this.width = (size.x*0.9).toInt()
                this.height = (size.y*0.3).toInt()
            }
        }

        fun bind(movieShort: MovieShort) {
            with(itemView) {
                if (movieShort.backdropPath != null) {
                    recommendations_movie_container.visibility = View.INVISIBLE
                    recommendations_movie_image.loadImg(
                        movieShort.backdropPath,
                        R.color.color_background_image,
                        callback = object : Callback {
                            override fun onSuccess() {
                                recommendations_movie_container.visibility = View.VISIBLE
                            }

                            override fun onError(e: Exception?) {
                                film_little_poster.loadImg(R.drawable.error_loading_image)
                                recommendations_movie_container.visibility = View.VISIBLE
                            }

                        }
                    )
                } else {
                    film_little_poster.loadImg(R.drawable.not_poster_image)
                }
                recommendations_movie_name.text = movieShort.title
            }
        }
    }
}