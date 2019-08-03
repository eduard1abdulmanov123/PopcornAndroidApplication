package com.abdulmanov.myapplication.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulmanov.myapplication.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ID_VIDEO= "ID_VIDEO"
        fun newIntent(context: Context, id:String): Intent {
            return Intent(context, VideoActivity::class.java).apply {
                putExtra(EXTRA_ID_VIDEO,id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        lifecycle.addObserver(video_player_view)
        video_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = intent!!.extras!!.getString(EXTRA_ID_VIDEO)
                youTubePlayer.loadVideo(videoId!!,0f)
            }
        })
    }

}
