package com.abdulmanov.MoviCorn.ui.search.filter_search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.MoviCorn.BaseApp


import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.adapters.LoadingDelegateAdapter
import com.abdulmanov.MoviCorn.adapters.MovieDelegateAdapter
import com.abdulmanov.MoviCorn.di.module.FragmentModule
import com.abdulmanov.MoviCorn.model.Filter
import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.common_ui.BaseFragment
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import com.abdulmanov.MoviCorn.ui.filters.FiltersActivity
import com.abdulmanov.MoviCorn.ui.search.string_search.StringSearchFragment
import com.abdulmanov.customviews.focus
import com.abdulmanov.customviews.recyclerview.CompositeDelegateAdapter
import com.abdulmanov.customviews.recyclerview.LinearInfiniteScrollListener
import com.abdulmanov.customviews.recyclerview.VerticalItemDecoration
import com.abdulmanov.domain.models.genres.Genre
import com.abdulmanov.domain.repositories.MoviesRepository
import kotlinx.android.synthetic.main.fragment_search_filter.*
import kotlinx.android.synthetic.main.fragment_string_search.*
import kotlinx.android.synthetic.main.layout_data_empty.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SearchFilterFragment : Fragment(),SearchFilterContract.View {

    companion object{
        private const val REQUEST_FILTER_ACTIVITY = 1
    }
    @Inject
    lateinit var presenter: SearchFilterContract.Presenter

    @Inject
    lateinit var moviesRepository: MoviesRepository

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var scrollListener: LinearInfiniteScrollListener
    private val loadingItem = LoadingDelegateAdapter.Loading()
    private var filter:Filter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.instance.appComponent.fragmentComponent(FragmentModule()).inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        presenter.loadGenres("ru-RU")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_FILTER_ACTIVITY&&resultCode==RESULT_OK){
            filter = data?.extras?.getParcelable(FiltersActivity.FILTER)
            presenter.changeRequestFactory {
                moviesRepository.discoverMovies(
                    it.toString(),
                    "ru-RU",
                    "RU",
                    filter!!.sort,
                    filter!!.genres.filter { genre-> genre.third }.map{genre-> genre.first }.joinToString("|"),
                    filter!!.voteAverageGte,
                    filter!!.voteAverageLte,
                    filter!!.releaseDateGte,
                    filter!!.releaseDateLte
                )
            }
        }
    }

    override fun createFilter(genres: List<Genre>) {
        filter = Filter(
            "vote_average.desc",
            genres.map { Triple(it.id,it.name,true)},
            6.0,
            10.0,
            "1990-01-01",
            (SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toInt()+1).toString()+"-01-01"
        )
        Log.d("SearchFilterFragment",filter.toString())
        Log.d("SearchFilterFragment",filter!!.genres.map {genre-> genre.first }.joinToString("|"))
        presenter.changeRequestFactory {
            moviesRepository.discoverMovies(
                it.toString(),
                "ru-RU",
                "RU",
                filter!!.sort,
                filter!!.genres.filter { genre-> genre.third }.map{genre-> genre.first }.joinToString("|"),
                filter!!.voteAverageGte,
                filter!!.voteAverageLte,
                filter!!.releaseDateGte,
                filter!!.releaseDateLte
            )
        }
    }

    override fun showEmptyProgress(show: Boolean) {
        if (show) {
            search_filter_recycler_view.visibility = View.GONE
            layout_progress_bar.visibility = View.VISIBLE
            scrollListener.setStartState()
        } else {
            search_filter_recycler_view.visibility = View.VISIBLE
            layout_progress_bar.visibility = View.GONE
        }
    }

    override fun showEmptyError(show: Boolean) {
        if (show) {
            search_filter_recycler_view.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
        } else {
            search_filter_recycler_view.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        }
    }

    override fun showEmptyData(show: Boolean) {
        if (show) {
            search_filter_recycler_view.visibility = View.GONE
            layout_data_empty.visibility = View.VISIBLE
        } else {
            search_filter_recycler_view.visibility = View.VISIBLE
            layout_data_empty.visibility = View.GONE
        }
    }

    override fun showData(data: List<Movie>, swap: Boolean) {
        if (swap) {
            linearLayoutManager.scrollToPosition(0)
            ( search_filter_recycler_view.adapter as CompositeDelegateAdapter).swapData(data)
        } else {
            ( search_filter_recycler_view.adapter as CompositeDelegateAdapter).addAllData(data)
        }
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
            search_filter_recycler_view.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
        } else {
            search_filter_recycler_view.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        }
    }

    override fun showPageProgress(show: Boolean) {
        if (show) {
            (search_filter_recycler_view.adapter as CompositeDelegateAdapter).addData(loadingItem)
        } else {
            (search_filter_recycler_view.adapter as CompositeDelegateAdapter).removeData(loadingItem)
        }
    }

    private fun initUI(){
        initRecyclerView()
        search_filter_search_view.setOnClickListener {
            (parentFragment as? BaseFragment)?.startFragment(StringSearchFragment.newInstance())
        }
        search_filter_toolbar.inflateMenu(R.menu.menu_search_filter)
        search_filter_toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.search_filter_add_filter ->{
                    startActivityForResult(
                        FiltersActivity.newIntent(context!!,filter!!),
                        REQUEST_FILTER_ACTIVITY
                    )
                }
            }
            return@setOnMenuItemClickListener true
        }
        button_refresh.setOnClickListener {
            if(filter==null) {
                presenter.reloadGenres("ru-RU")
            }else{
                presenter.refresh()
            }
        }
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        scrollListener = LinearInfiniteScrollListener(linearLayoutManager, 0) { presenter.loadNextPage() }

        val movieDelegateAdapter =
            MovieDelegateAdapter ({
                startActivity(DetailsMovieActivity.newIntent(context!!, it))
            })
        val adapterCustom = CompositeDelegateAdapter.Builder()
            .add(LoadingDelegateAdapter())
            .add(movieDelegateAdapter)
            .build()
        with(search_filter_recycler_view) {
            addOnScrollListener(scrollListener)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecoration(16, 4, context))
            layoutManager = linearLayoutManager
            adapter = adapterCustom
        }
    }
}
