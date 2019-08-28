package com.abdulmanov.MoviCorn.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.MoviCorn.model.vo.movie.FilmLittle
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.item_list_film_little.view.*
import kotlinx.android.synthetic.main.item_list_video.view.*
import java.lang.Exception

class FilmLittleAdapter(private val clickListener: (id:Long)->Unit):RecyclerView.Adapter<FilmLittleAdapter.SimilarHolder>(){

    private val similarMovies = mutableListOf<FilmLittle>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarHolder {
        return SimilarHolder(parent)
    }

    override fun getItemCount(): Int {
        return similarMovies.size
    }

    override fun onBindViewHolder(holder: SimilarHolder, position: Int) {
       holder.bind(similarMovies[position])
    }

    fun add(data:List<FilmLittle>){
        similarMovies.addAll(data)
        notifyDataSetChanged()
    }

    inner class SimilarHolder(parent: ViewGroup):RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_film_little)){
        init {
            itemView.setOnClickListener {
                clickListener.invoke(similarMovies[adapterPosition].id)
            }
        }

        fun bind(filmLittle: FilmLittle){
            with(itemView){
                if(filmLittle.posterPath!=null) {
                    film_little_poster.loadImg(
                        filmLittle.posterPath,
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
                film_little_name.text = filmLittle.title
                film_little_release_data.text = filmLittle.releaseDate ?: ""
                film_little_vote_average.text = filmLittle.voteAverage
            }
        }
    }
}