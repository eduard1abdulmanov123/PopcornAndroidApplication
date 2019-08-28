package com.abdulmanov.MoviCorn.ui.library

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.customviews.recyclerview.IDelegateAdapter
import com.abdulmanov.customviews.recyclerview.ItemView
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.MoviCorn.model.vo.FilmLibrary
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_library.view.*

class MovieLibraryDelegateAdapter(
    private val clickListener:(id:Long)->Unit,
    private val clickDeleteListener:(film:FilmLibrary)->Unit
):IDelegateAdapter<MovieLibraryDelegateAdapter.MovieLibraryViewHolder,FilmLibrary> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieLibraryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MovieLibraryViewHolder, item: FilmLibrary) {
        holder.bind(item)
    }

    override fun onRecycled(holder: MovieLibraryViewHolder) {
        super.onRecycled(holder)
        holder.remove()
    }

    override fun isForViewType(item: ItemView): Boolean {
        return item is FilmLibrary
    }

    inner class MovieLibraryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_library)) {

        fun bind(item: FilmLibrary) {
            with(itemView) {
                setOnClickListener { clickListener.invoke(item.id) }
                library_delete_movie.setOnClickListener { clickDeleteListener.invoke(item) }
                library_movie_poster.loadImg(item.posterPath)
                library_container_for_genres.text = item.genres.toString()
                library_movie_name.text = item.title
                library_movie_release.text = item.releaseDate
                library_movie_overview.text = item.overview
            }
        }

        fun remove(){
            with(itemView) {
                    Picasso.get().cancelRequest(library_movie_poster)
                    library_movie_poster.setImageDrawable(null)
            }
        }
    }
}