package com.abdulmanov.MoviCorn.ui.movie.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.MoviCorn.BaseApp
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.adapters.FilmLittleAdapter
import com.abdulmanov.MoviCorn.adapters.RecommendationsMoviesAdapter
import com.abdulmanov.MoviCorn.common.initHorizontalRecyclerView
import com.abdulmanov.MoviCorn.di.module.FragmentModule
import com.abdulmanov.domain.models.movies.PackageMovies
import com.abdulmanov.MoviCorn.ui.common_ui.BaseFragment
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import com.abdulmanov.MoviCorn.ui.movie.list.MovieListFragment
import com.abdulmanov.MoviCorn.ui.movie.list.MovieListFragment.Companion
import com.abdulmanov.MoviCorn.ui.movie.list.MovieListFragment.Companion.TypeMovieList.*
import com.abdulmanov.customviews.recyclerview.HorizontalItemDecoration
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.android.synthetic.main.fragment_movie_main.*
import kotlinx.android.synthetic.main.layout_error.*
import javax.inject.Inject


class MovieMainFragment : Fragment(), MovieMainContract.View {

    @Inject
    lateinit var presenter: MovieMainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.instance.appComponent.fragmentComponent(FragmentModule()).inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movie_main_toolbar.title = context?.getString(R.string.movie_main_title_toolbar)
        initUI()
        presenter.loadData("ru-RU", "RU")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showEmptyProgress(show: Boolean) {
        if (show) {
            layout_progress_bar.visibility = View.VISIBLE
            movie_main_content.visibility = View.GONE
        } else {
            layout_progress_bar.visibility = View.GONE
            movie_main_content.visibility = View.VISIBLE
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
            layout_error.visibility = View.VISIBLE
            movie_main_content.visibility = View.GONE
        } else {
            layout_error.visibility = View.GONE
            movie_main_content.visibility = View.VISIBLE
        }
    }

    override fun showData(data: PackageMovies) {
        (recommendations_recycler_view.adapter as RecommendationsMoviesAdapter).add(data.recommendations)
        (now_playing_recycler_view.adapter as FilmLittleAdapter).add(data.nowPlaying)
        (upcoming_recycler_view.adapter as FilmLittleAdapter).add(data.upcoming)
        (popular_recycler_view.adapter as FilmLittleAdapter).add(data.popular)
        (top_rated_recycler_view.adapter as FilmLittleAdapter).add(data.topRated)
    }

    private fun initUI() {
        button_refresh.setOnClickListener {
            presenter.refresh("ru-RU", "RU")
        }

        initViewAll(now_playing_view_all,NOW_PLAYING)
        initViewAll(upcoming_view_all,UPCOMING)
        initViewAll(popular_view_all,POPULAR)
        initViewAll(top_rated_view_all,TOP_RATED)

        initRecyclerView(recommendations_recycler_view,1,6,2)
        initRecyclerView(now_playing_recycler_view,0,4,2)
        initRecyclerView(upcoming_recycler_view,0,4,2)
        initRecyclerView(popular_recycler_view,0,4,2)
        initRecyclerView(top_rated_recycler_view,0,4,2)
    }

    private fun initRecyclerView(recyclerView: RecyclerView, typeAdapter:Int, offsetLeftRight:Int,offsetTopBottom:Int){
        if(typeAdapter==0){
            recyclerView.initHorizontalRecyclerView(FilmLittleAdapter{
                startActivity(DetailsMovieActivity.newIntent(context!!, it))
            })
        }else{
            recyclerView.initHorizontalRecyclerView(RecommendationsMoviesAdapter{
                startActivity(DetailsMovieActivity.newIntent(context!!, it))
            })
        }
        recyclerView.addItemDecoration(HorizontalItemDecoration(offsetLeftRight,offsetTopBottom,context!!))
    }

    private fun initViewAll(view: TextView, typeMovieList: Companion.TypeMovieList){
        view.setOnClickListener {
            (parentFragment as? BaseFragment)?.startFragment(MovieListFragment.newInstance(typeMovieList))
        }
    }
}
