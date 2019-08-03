package com.abdulmanov.myapplication.ui.details

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.abdulmanov.myapplication.R
import com.abdulmanov.myapplication.common.inflate
import com.abdulmanov.myapplication.common.loadImg
import com.abdulmanov.myapplication.model.vo.Video
import kotlinx.android.synthetic.main.video_item.view.*

class VideosAdapter(private val clickListener: (path:String)->Unit): RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private val videos = mutableListOf<Video>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): VideosAdapter.VideoViewHolder {
        return VideoViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(viewHolder: VideosAdapter.VideoViewHolder, position: Int) {
        viewHolder.bind(videos[position],clickListener)
    }

    fun add(data:List<Video>){
        videos.addAll(data)
    }

    inner class VideoViewHolder(val parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.video_item)){

        fun bind(video: Video,clickListener: (path: String) -> Unit){
            with(itemView){
                setOnClickListener { clickListener.invoke(video.pathUrl) }
                video_image.loadImg(video.thumbnail,R.drawable.camera)
            }
        }
    }

}