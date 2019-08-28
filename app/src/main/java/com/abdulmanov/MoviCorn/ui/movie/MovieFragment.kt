package com.abdulmanov.MoviCorn.ui.movie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.di.component.FragmentComponent
import com.abdulmanov.MoviCorn.ui.common_ui.BaseMovieFragment
import io.reactivex.Single
import kotlinx.android.synthetic.main.content_empty_progress_bar.view.*
import kotlinx.android.synthetic.main.content_error.view.*
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

    override fun installRecycleView(view: View): RecyclerView = view.recycler_view

    override fun installRefreshButton(view: View): View? = view.button_refresh

    override fun installEmptyProgressBar(view: View): View = view.empty_progress_bar

    override fun installRefreshProgressBar(view: View): View? = view.refresh_progress_bar

    override fun installContainerErrorView(view: View): View? = view.container_error

    override fun containerEmptyData(view: View): View? = null

    override fun injectDependency(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getTypeFunctionForFilm(): (page: Int) -> Single<MoviesDTO> {
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
