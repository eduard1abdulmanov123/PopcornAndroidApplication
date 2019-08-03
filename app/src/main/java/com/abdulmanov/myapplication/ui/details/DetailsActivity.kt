package com.abdulmanov.myapplication.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulmanov.core.network.common.isNetworkAvailable
import com.abdulmanov.myapplication.BaseApp
import com.abdulmanov.myapplication.common.loadImg
import com.abdulmanov.myapplication.di.module.ActivityModule
import com.abdulmanov.myapplication.model.mappers.FilmDetailsMapper
import com.abdulmanov.myapplication.model.vo.FilmDetails
import com.abdulmanov.myapplication.model.vo.FilmShort
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.empty_progress_bar.*
import kotlinx.android.synthetic.main.error_container.*
import javax.inject.Inject
import com.abdulmanov.myapplication.R


class DetailsActivity : AppCompatActivity(),DetailsContract.View {

    companion object {
        private const val EXTRA_FILM_ID = "FILM_ID"
        fun newIntent(context: Context, id:Long): Intent {
            return Intent(context,DetailsActivity::class.java).apply {
                putExtra(EXTRA_FILM_ID,id)
            }
        }
    }

    @Inject
    lateinit var presenter: DetailsContract.Presenter
    private var flagVisibleAddedBtn = true
    lateinit var filmDetails: FilmDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(details_tool_bar)
        supportActionBar?.title = ""
        initUI()
        BaseApp.instance.appComponent.activityComponent(ActivityModule()).inject(this)
        presenter.attach(this, FilmDetailsMapper(baseContext))
        presenter.loadData((intent.extras?.get(EXTRA_FILM_ID) as Long),"ru-RU")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showEmptyProgress(show: Boolean) {
        if(show){
            empty_progress_bar.visibility = View.VISIBLE
            container_data.visibility = View.GONE
            if(flagVisibleAddedBtn)
                edit_film_btn.hide()
        }else{
            empty_progress_bar.visibility = View.GONE
            container_data.visibility = View.VISIBLE
            if(flagVisibleAddedBtn)
                edit_film_btn.show()
        }
    }

    override fun showRefreshProgress(show: Boolean) {
       if(show){
           button_refresh.visibility = View.INVISIBLE
           refresh_progress_bar.visibility = View.VISIBLE
       }else{
           button_refresh.visibility = View.VISIBLE
           refresh_progress_bar.visibility = View.INVISIBLE
       }
    }

    override fun showErrorMessage(show: Boolean, error: Throwable?) {
        if(show){
            container_error.visibility = View.VISIBLE
            container_data.visibility = View.GONE
            if(flagVisibleAddedBtn)
                edit_film_btn.hide()
        }else{
            container_error.visibility = View.GONE
            container_data.visibility = View.VISIBLE
            if(flagVisibleAddedBtn)
                edit_film_btn.show()
        }
    }

    override fun showMessage(str: String) {
        Snackbar.make(coordinator_layout,str,Snackbar.LENGTH_SHORT).show()
    }

    override fun showData(data:FilmDetails) {
        filmDetails = data
        film_poster.layoutParams.height = (root_view.height*0.8).toInt()
        film_poster.loadImg(data.posterPath)
        film_title.text = data.title
        film_original_title.text=data.originalTitle
        film_released.text = data.releaseData
        film_vote_average.text = data.voteAverage
        film_vote_count.text = data.voteCount

        if(data.countries.isEmpty()){
            container_country.visibility = View.GONE
        }else{
            film_countries.text = data.countries.joinToString(", ")
        }

        if(data.genres.isEmpty()){
            container_genre.visibility = View.GONE
        }else{
            film_genres.text = data.genres.joinToString(", ")
        }

        if(data.runtime.isEmpty()){
            container_time.visibility = View.GONE
        }else{
            film_runtime.text = data.runtime
        }

        film_budget.text = data.budget
        film_revenue.text = data.revenue

        if(data.overview.isEmpty()){
            container_overview.visibility = View.GONE
        }else{
            film_overview.text = data.overview
        }

        if(data.credits.isEmpty()){
            container_credits.visibility = View.GONE
        }else{
            (recycler_view_credit.adapter as CreditsAdapter).add(data.credits)
        }
        if(data.videos.isEmpty()|| !baseContext.isNetworkAvailable()){
            container_videos.visibility = View.GONE
        }else {
            (recycler_view_video.adapter as VideosAdapter).add(data.videos)
        }
    }

    override fun showAddedButton(show: Boolean) {
        if(show){
            flagVisibleAddedBtn = true
            edit_film_btn.show()
        }else{
            flagVisibleAddedBtn = false
            edit_film_btn.hide()
        }
    }

    override fun getContext(): Context {
        return baseContext
    }

    private fun initUI() {
        back_button.setOnClickListener { finish() }
        button_refresh.setOnClickListener {
            presenter.refresh((intent.extras?.get(EXTRA_FILM_ID) as Long), "ru-RU")
        }
        edit_film_btn.setOnClickListener {
            presenter.addFilmInLibrary(filmDetails)
        }
        container_data.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if(flagVisibleAddedBtn) {
                if (scrollY > oldScrollY && edit_film_btn.visibility == View.VISIBLE) {
                    edit_film_btn.hide()
                } else if (scrollY < oldScrollY && edit_film_btn.visibility != View.VISIBLE) {
                    edit_film_btn.show()
                }
            }
        }
        initCreditsRecyclerView()
        initVideosRecyclerView()
        showAddedButton(false)
    }

    private fun initCreditsRecyclerView(){
        val creditsAdapter = CreditsAdapter()
        with(recycler_view_credit){
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = creditsAdapter
        }
    }

    private fun initVideosRecyclerView(){
        val videosAdapter = VideosAdapter { startActivity(VideoActivity.newIntent(baseContext,it)) }
        with(recycler_view_video){
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = videosAdapter
        }
    }

}
