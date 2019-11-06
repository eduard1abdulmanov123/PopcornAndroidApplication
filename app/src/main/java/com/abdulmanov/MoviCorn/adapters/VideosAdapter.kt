package com.abdulmanov.MoviCorn.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.inflate
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.domain.models.movies.Video
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.item_list_video.view.*
import java.lang.Exception

class VideosAdapter(private val clickListener: (path:String)->Unit): RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private val videos = mutableListOf<Video>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): VideoViewHolder {
        return VideoViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(viewHolder: VideoViewHolder, position: Int) {
        viewHolder.bind(videos[position])
    }

    fun add(data:List<Video>){
        videos.addAll(data)
        notifyDataSetChanged()
    }

    inner class VideoViewHolder(val parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_video)){

        init {
            itemView.setOnClickListener {
                clickListener.invoke(videos[adapterPosition].url)
            }
        }

        fun bind(video: Video){
            with(itemView){
                video_image.loadImg(
                    video.thumbnail,
                    R.color.color_background_image,
                    callback = object : Callback{
                        override fun onSuccess() {
                            itemView.play_video_image.visibility = View.VISIBLE
                        }

                        override fun onError(e: Exception?) {
                            video_image.loadImg(R.drawable.error_loading_image)
                        }
                    }
                )
            }
        }
    }

}