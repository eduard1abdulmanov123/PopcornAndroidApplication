package com.abdulmanov.MoviCorn.adapters

import android.content.Context
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.MoviCorn.model.Movie
import kotlinx.android.synthetic.main.item_list_little_poster.view.*



class LibraryActionAdapter(
    private val clickListener: (count:Int)->Unit
):RecyclerView.Adapter<LibraryActionAdapter.LibraryActionViewHolder>() {

    private val movies = mutableListOf<Movie>()
    private val selectedItems: SparseBooleanArray = SparseBooleanArray()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryActionViewHolder {
        return LibraryActionViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: LibraryActionViewHolder, position: Int) {
        holder.bind(movies[position],position)
    }

    fun addData(movies:List<Movie>){
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun remove(position:Int){
        movies.removeAt(position)
        notifyItemRemoved(position)
    }

    fun toggleSelection(pos: Int){
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos)
        } else {
            selectedItems.put(pos, true)
        }
        notifyItemChanged(pos)
    }

    fun clearSelections() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun clearSelectionsData(){
        movies.removeAll(getSelectedData())
        notifyDataSetChanged()
    }

    fun selectAll(){
        for(i in movies.indices){
            if(!selectedItems.get(i,false)){
                toggleSelection(i)
            }
        }
    }

    fun getSelectedItemCount(): Int {
        return selectedItems.size()
    }

    fun getSelectedItems(): List<Int> {
        val items = ArrayList<Int>(selectedItems.size())
        for (i in 0 until selectedItems.size()) {
            items.add(selectedItems.keyAt(i))
        }
        return items
    }

    fun getSelectedData():List<Movie>{
        val data = mutableListOf<Movie>()
        for(i in 0 until selectedItems.size()){
            data.add(movies[selectedItems.keyAt(i)])
        }
        return data
    }

    inner class LibraryActionViewHolder(parent: ViewGroup):RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_little_poster)){
        private val monthArray =  itemView.context.resources.getStringArray(R.array.month)

        init {
            itemView.setOnClickListener {
                toggleSelection(adapterPosition)
                clickListener.invoke(selectedItems.size())
            }
        }

        fun bind(item: Movie,position: Int) {
            with(itemView) {
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

                if(selectedItems.get(position, false)){
                   root_view.setBackgroundColor(ContextCompat.getColor(context,R.color.color_selected))
                }else{
                    root_view.setBackgroundColor(ContextCompat.getColor(context,android.R.color.white))
                }
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