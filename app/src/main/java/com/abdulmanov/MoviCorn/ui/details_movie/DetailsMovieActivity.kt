package com.abdulmanov.MoviCorn.ui.details_movie

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import com.abdulmanov.MoviCorn.BaseApp
import com.abdulmanov.MoviCorn.di.module.ActivityModule
import com.abdulmanov.MoviCorn.model.vo.movie.DetailsMovie
import kotlinx.android.synthetic.main.content_empty_progress_bar.*
import kotlinx.android.synthetic.main.content_error.*
import javax.inject.Inject
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.ui.details_person.DetailsPersonActivity
import com.abdulmanov.MoviCorn.adapters.CreditsAdapter
import com.abdulmanov.MoviCorn.adapters.FilmLittleAdapter
import com.abdulmanov.MoviCorn.adapters.VideosAdapter
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.YOUTUBE_WATCH_BASE_URL
import com.abdulmanov.MoviCorn.common.initHorizontalRecyclerView
import com.abdulmanov.MoviCorn.common.loadImg
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.activity_details_movie.*
import kotlinx.android.synthetic.main.content_movie_details.*
import java.lang.Exception


class DetailsMovieActivity : AppCompatActivity(),DetailsMovieContract.View {

    companion object {
        private const val EXTRA_FILM_ID = "FILM_ID"
        fun newIntent(context: Context, id: Long): Intent {
            return Intent(context, DetailsMovieActivity::class.java).apply {
                putExtra(EXTRA_FILM_ID, id)
            }
        }
    }

    @Inject
    lateinit var mPresenter: DetailsMovieContract.Presenter
    lateinit var detailsMovie: DetailsMovie
    lateinit var saveMovieInLibraryMenuItem:MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_movie)
        setSupportActionBar(details_movie_toolbar)
        initUI()
        BaseApp.instance.appComponent.activityComponent(ActivityModule(this)).inject(this)
        mPresenter.attach(this)
        mPresenter.loadData(intent.extras?.get(EXTRA_FILM_ID) as Long, "ru-RU")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details_movie,menu)
        saveMovieInLibraryMenuItem = menu!!.findItem(R.id.details_movie_save_in_library)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.details_movie_send -> {
                val intentSend = Intent(Intent.ACTION_SEND)
                intentSend.type = "text/plain"
                intentSend.putExtra(Intent.EXTRA_TEXT, detailsMovie.movieDbID)
                startActivity(intentSend)
                true
            }
            R.id.details_movie_save_in_library-> {
                mPresenter.saveMovieInLibrary(detailsMovie)
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
    }

    override fun showEmptyProgress(show: Boolean) {
        if (show) {
            empty_progress_bar.visibility = View.VISIBLE
            container_details_movie.visibility = View.GONE
        } else {
            empty_progress_bar.visibility = View.GONE
            container_details_movie.visibility = View.VISIBLE
        }
    }

    override fun showRefreshProgress(show: Boolean) {
        if (show) {
            button_refresh.visibility = View.INVISIBLE
            refresh_progress_bar.visibility = View.VISIBLE
        } else {
            button_refresh.visibility = View.VISIBLE
            refresh_progress_bar.visibility = View.INVISIBLE
        }
    }

    override fun showError(show: Boolean, error: Throwable?) {
        if (show) {
            container_error.visibility = View.VISIBLE
            container_details_movie.visibility = View.GONE
        } else {
            container_error.visibility = View.GONE
            container_details_movie.visibility = View.VISIBLE
        }
    }

    override fun showMessage(stringId: Int) {
        val str = baseContext.resources.getString(stringId)
        Snackbar.make(container_details_movie, str, Snackbar.LENGTH_SHORT).show()
    }

    override fun showSaved(show: Boolean) {
        val res = if (show) R.drawable.ic_saved else R.drawable.ic_save
        saveMovieInLibraryMenuItem.icon = ContextCompat.getDrawable(this,res)
    }

    override fun showData(data: DetailsMovie) {
        detailsMovie = data
        if(data.existsInTheDB){
            showSaved(true)
        }

        if(data.posterPath!=null) {
            details_movie_poster_image.loadImg(
                data.posterPath,
                R.drawable.placeholder_image,
                callback = object : Callback{
                   override fun onSuccess() {}

                   override fun onError(e: Exception?) {
                       details_movie_poster_image.loadImg(R.drawable.error_loading_image)
                   }

                }
            )
        }else{
            details_movie_poster_image.loadImg(R.drawable.not_poster_image)
        }

        details_movie_title.text = data.title
        details_movie_original_title.text = data.originalTitle
        details_movie_released.text = data.releaseData
        details_movie_vote_average.text = data.voteAverage.toString()
        details_movie_vote_count.text =
            if (data.voteCount == 0.toLong())
                baseContext.getString(R.string.vote_count_zero)
            else
                baseContext.resources.getQuantityString(
                    R.plurals.vote_count,
                    data.voteCount.toInt(),
                    data.voteCount.toInt()
                )

        if (!data.countries.isNullOrEmpty()) {
            container_details_movie_country.visibility = View.VISIBLE
            details_movie_countries.text = data.countries.joinToString(", ")
        }

        if (!data.genres.isNullOrEmpty()) {
            container_details_movie_genre.visibility = View.VISIBLE
            details_movie_genres.text = data.genres.joinToString(", ")
        }

        if (!data.runtime.isNullOrEmpty()) {
            container_details_movie_time.visibility = View.VISIBLE
            details_movie_runtime.text = data.runtime
        }

        details_movie_budget.text = data.budget
        details_movie_revenue.text = data.revenue

        if (!data.overview.isNullOrEmpty()) {
            container_details_movie_overview.visibility = View.VISIBLE
            details_movie_overview.text = data.overview
        }

        if (!data.movieCredits.isNullOrEmpty()) {
            container_details_movie_credits.visibility = View.VISIBLE
            (recycler_view_credits.adapter as CreditsAdapter).add(data.movieCredits)
        }

        if (!data.movieVideos.isNullOrEmpty()) {
            container_details_movie_videos.visibility = View.VISIBLE
            (recycler_view_videos.adapter as VideosAdapter).add(data.movieVideos)
        }

        if(!data.similar.isNullOrEmpty()){
            container_details_movie_similar.visibility = View.VISIBLE
            (recycler_view_similar.adapter as FilmLittleAdapter).add(data.similar)
        }
    }

    private fun initUI() {
        window.setBackgroundDrawable(null)
        title = ""

        details_movie_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
        details_movie_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        details_movie_app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(appBarLayout.totalScrollRange+verticalOffset==0){
                details_movie_toolbar.background = null
                details_movie_toolbar_layout.title = detailsMovie.title
            }else{
                details_movie_toolbar.setBackgroundResource(R.drawable.rectangle_gray_transparent_gradient)
                details_movie_toolbar_layout.title = ""
            }
        })

       details_movie_root_view.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                details_movie_root_view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                details_movie_toolbar_layout.layoutParams.height = (0.8*details_movie_root_view.height).toInt()
            }
        })

        button_refresh.setOnClickListener {
            mPresenter.refresh((intent.extras?.get(EXTRA_FILM_ID) as Long), "ru-RU")
        }

        recycler_view_videos.initHorizontalRecyclerView(
            VideosAdapter {
                val address = Uri.parse(YOUTUBE_WATCH_BASE_URL+it)
                startActivity(Intent(Intent.ACTION_VIEW,address))
            }
        )

        recycler_view_credits.initHorizontalRecyclerView(
            CreditsAdapter{
                startActivity(DetailsPersonActivity.newIntent(baseContext,it))
            }
        )

        recycler_view_similar.initHorizontalRecyclerView(
            FilmLittleAdapter{
                startActivity(newIntent(baseContext,it))
            }
        )
    }

}
