package com.abdulmanov.MoviCorn.ui.movie.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.MoviCorn.BaseApp
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.di.module.FragmentModule
import com.abdulmanov.MoviCorn.adapters.LoadingDelegateAdapter
import com.abdulmanov.MoviCorn.adapters.MovieDelegateAdapter
import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import com.abdulmanov.MoviCorn.ui.movie.list.MovieListFragment.Companion.TypeMovieList.*
import com.abdulmanov.customviews.recyclerview.CompositeDelegateAdapter
import com.abdulmanov.customviews.recyclerview.LinearInfiniteScrollListener
import com.abdulmanov.customviews.recyclerview.VerticalItemDecoration
import com.abdulmanov.domain.repositories.MoviesRepository
import io.reactivex.Single
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.layout_error.*
import javax.inject.Inject


class MovieListFragment : Fragment(), MovieListContract.View {

    companion object {
        private const val KEY_MOVIE_LIST_TYPE = "KEY_MOVIE_LIST_TYPE"

        @JvmStatic
        fun newInstance(movieListType: TypeMovieList) =
            MovieListFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_MOVIE_LIST_TYPE, movieListType.name)
                }
            }

        enum class TypeMovieList {
            NOW_PLAYING, UPCOMING, POPULAR, TOP_RATED
        }
    }

    @Inject
    lateinit var presenter: MovieListContract.Presenter
    @Inject
    lateinit var model: MoviesRepository

    private lateinit var typeMovieList: TypeMovieList
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val loadingItem = LoadingDelegateAdapter.Loading()
    private var showBackToTop = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        typeMovieList = TypeMovieList.valueOf(arguments?.getString(KEY_MOVIE_LIST_TYPE)!!)
        BaseApp.instance.appComponent.fragmentComponent(FragmentModule()).inject(this)
        presenter.attach(this, getRequestFactory(typeMovieList))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_movie_list_toolbar.title = getTitleToolbar(typeMovieList)
        initUI()
        presenter.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showEmptyProgress(show: Boolean) {
        if (show) {
            fragment_movie_list_recycler_view.visibility = View.GONE
            fragment_movie_list_back_to_top.visibility = View.GONE
            layout_progress_bar.visibility = View.VISIBLE
        } else {
            fragment_movie_list_recycler_view.visibility = View.VISIBLE
            fragment_movie_list_back_to_top.visibility = View.VISIBLE
            layout_progress_bar.visibility = View.GONE
        }
    }

    override fun showEmptyError(show: Boolean) {
        if (show) {
            fragment_movie_list_recycler_view.visibility = View.GONE
            fragment_movie_list_back_to_top.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
        } else {
            fragment_movie_list_recycler_view.visibility = View.VISIBLE
            fragment_movie_list_back_to_top.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        }
    }

    override fun showData(data: List<Movie>, swap: Boolean) {
        (fragment_movie_list_recycler_view.adapter as CompositeDelegateAdapter).addAllData(data)
    }

    override fun showFailProgress(show: Boolean) {
        if (show) {
            button_refresh.visibility = View.INVISIBLE
            refresh_progress_bar.visibility = View.VISIBLE
        } else {
            button_refresh.visibility = View.VISIBLE
            refresh_progress_bar.visibility = View.INVISIBLE
        }
    }

    override fun showErrorMessage(show: Boolean) {
        if (show) {
            fragment_movie_list_recycler_view.visibility = View.GONE
            fragment_movie_list_back_to_top.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
        } else {
            fragment_movie_list_recycler_view.visibility = View.VISIBLE
            fragment_movie_list_back_to_top.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        }
    }

    override fun showPageProgress(show: Boolean) {
        if (show) {
            (fragment_movie_list_recycler_view.adapter as CompositeDelegateAdapter).addData(loadingItem)
        } else {
            (fragment_movie_list_recycler_view.adapter as CompositeDelegateAdapter).removeData(loadingItem)
        }
    }


    private fun initUI() {
        initRecyclerViewAndFAB()

        fragment_movie_list_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
        fragment_movie_list_toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        button_refresh.setOnClickListener {
            presenter.refresh()
        }
        fragment_movie_list_back_to_top.setOnClickListener {
            linearLayoutManager.scrollToPosition(0)
            fragment_movie_list_recycler_view.stopScroll()
            fragment_movie_list_app_bar_layout.setExpanded(true,false)
        }
    }

    private fun initRecyclerViewAndFAB() {
        linearLayoutManager = LinearLayoutManager(context)
        val scrollListener =
            object : LinearInfiniteScrollListener(linearLayoutManager, 0, { presenter.loadNextPage() }) {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (linearLayoutManager.findFirstVisibleItemPosition() == 0 && showBackToTop) {
                        fragment_movie_list_back_to_top.hide()
                        showBackToTop = false
                    } else if (linearLayoutManager.findFirstVisibleItemPosition() != 0 && !showBackToTop) {
                        fragment_movie_list_back_to_top.show()
                        showBackToTop = true
                    }
                }
            }
        val movieDelegateAdapter =
            MovieDelegateAdapter ({
                startActivity(DetailsMovieActivity.newIntent(context!!, it))
            })
        val adapterCustom = CompositeDelegateAdapter.Builder()
            .add(LoadingDelegateAdapter())
            .add(movieDelegateAdapter)
            .build()
        with(fragment_movie_list_recycler_view) {
            addOnScrollListener(scrollListener)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecoration(16,4,context))
            layoutManager = linearLayoutManager
            adapter = adapterCustom
        }
    }

    private fun getRequestFactory(typeMovieList: TypeMovieList): (page: Int) -> Single<List<com.abdulmanov.domain.models.movies.MovieMedium>> {
        return when (typeMovieList) {
            NOW_PLAYING -> { page: Int ->
                model.fetchNowPlayingMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }

            UPCOMING -> { page: Int ->
                model.fetchUpcomingMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }

            POPULAR -> { page: Int ->
                model.fetchPopularMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }

            TOP_RATED -> { page: Int ->
                model.fetchTopRatedMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }
        }
    }

    private fun getTitleToolbar(typeMovieList: TypeMovieList): String? {
        return context?.getString(
            when (typeMovieList) {
                NOW_PLAYING -> R.string.movie_main_now_playing
                UPCOMING -> R.string.movie_main_upcoming
                POPULAR -> R.string.movie_main_popular
                TOP_RATED -> R.string.movie_main_top_rated
            }
        )
    }
}
