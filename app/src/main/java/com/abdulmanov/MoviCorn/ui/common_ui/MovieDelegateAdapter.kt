package com.abdulmanov.MoviCorn.ui.common_ui
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.abdulmanov.customviews.recyclerview.IDelegateAdapter
import com.abdulmanov.customviews.recyclerview.ItemView
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.MoviCorn.model.vo.movie.FilmMedium
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_little_poster.view.*
import kotlinx.android.synthetic.main.item_list_big_poster.view.*




class MovieDelegateAdapter(
    private val layout: Int,
    private val clickListener: (id:Long)->Unit
) : IDelegateAdapter<MovieDelegateAdapter.MovieViewHolder, FilmMedium> {

    private var customHeight:Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = MovieViewHolder(parent)
        if(layout == R.layout.item_list_big_poster) {
            if(customHeight==null){
                customHeight =  (parent.height * 0.9).toInt()
            }
            holder.itemView.layoutParams.height = customHeight!!
        }
        return holder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, item: FilmMedium) {
        holder.bind(item)
    }

    override fun isForViewType(item: ItemView) = item is FilmMedium

    override fun onRecycled(holder: MovieViewHolder) {
        super.onRecycled(holder)
        holder.remove()
    }

    inner class MovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(layout)) {
        fun bind(item: FilmMedium) {
            with(itemView) {
                setOnClickListener { clickListener.invoke(item.id) }
                if (layout == R.layout.item_list_big_poster) {
                    big_movie_poster.loadImg(item.posterPath)
                    big_movie_name.text = item.title
                    big_container_for_genres.text = item.genres
                } else if (layout == R.layout.item_list_little_poster) {
                    little_movie_poster.loadImg(item.posterPath,R.drawable.rectangle_gray_transparent)
                    little_movie_release.text = item.releaseDate
                    little_movie_name.text = item.title
                    little_movie_vote_average.text = item.voteAverage
                    little_movie_vote_count.text = item.voteCount
                    little_container_for_genres.text = item.genres
                }
            }
        }

        fun remove(){
            with(itemView) {
                if (layout == R.layout.item_list_big_poster) {
                    Picasso.get().cancelRequest(big_movie_poster)
                    big_movie_poster.setImageDrawable(null)
                } else if (layout == R.layout.item_list_little_poster) {
                    Picasso.get().cancelRequest(little_movie_poster)
                    little_movie_poster.setImageDrawable(null)
                }
            }
        }
    }
}