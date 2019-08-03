package com.abdulmanov.myapplication.ui.movie

import android.util.Log
import android.view.View
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.myapplication.R
import com.abdulmanov.myapplication.di.component.FragmentComponent
import com.abdulmanov.myapplication.ui.common_ui.BaseMovieFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.empty_progress_bar.view.*
import kotlinx.android.synthetic.main.error_container.view.*
import kotlinx.android.synthetic.main.fragment_movie.view.*


class MovieFragment : BaseMovieFragment() {

    companion object {

        const val NOW_PLAYING_TAG ="NOW_PLAYING_TAG"
        const val UPCOMING_TAG ="UPCOMING_TAG"
        const val TOP_RATED_TAG ="TOP_RATED_TAG"

        @JvmStatic
        fun newInstance(): MovieFragment {
            return MovieFragment().apply {}
        }
    }

    override fun initViewFragment(view: View) {
        Log.d("MovieFragment",tag)
        buttonRefresh = view.button_refresh
        refreshProgressBar = view.refresh_progress_bar
        emptyProgressBar = view.empty_progress_bar
        containerErrorView = view.container_error
        recyclerView = view.recycler_view
    }

    override fun injectDependency(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getTypeFunctionForFilm(): (page: Int) -> Observable<MoviesDTO> {
        return when (tag) {
            NOW_PLAYING_TAG -> { page: Int ->
                networkModel.getNowPlayingMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }
            TOP_RATED_TAG -> { page: Int ->
                networkModel.getTopRatedMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }
            UPCOMING_TAG -> { page: Int ->
                networkModel.getUpcomingMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }
            else->{ page: Int ->
                networkModel.getNowPlayingMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }
        }
    }

    override fun getTypeLayoutForFragment() = R.layout.fragment_movie

    override fun getTypeLayoutForFilm(): Int {
        return  when (tag) {
            NOW_PLAYING_TAG -> R.layout.item_list_big_poster
            TOP_RATED_TAG -> R.layout.item_list_little_poster
            UPCOMING_TAG -> R.layout.item_list_big_poster
            else-> R.layout.item_list_big_poster
        }
    }
}
