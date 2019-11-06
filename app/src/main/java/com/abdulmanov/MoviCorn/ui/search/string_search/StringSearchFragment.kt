package com.abdulmanov.MoviCorn.ui.search.string_search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import com.abdulmanov.MoviCorn.BaseApp

import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.adapters.MovieDelegateAdapter
import com.abdulmanov.MoviCorn.di.module.FragmentModule
import com.abdulmanov.MoviCorn.adapters.LoadingDelegateAdapter
import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import com.abdulmanov.customviews.focus
import com.abdulmanov.customviews.recyclerview.CompositeDelegateAdapter
import com.abdulmanov.customviews.recyclerview.LinearInfiniteScrollListener
import com.abdulmanov.customviews.recyclerview.VerticalItemDecoration
import com.abdulmanov.domain.repositories.MoviesRepository
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.android.synthetic.main.fragment_string_search.*
import kotlinx.android.synthetic.main.layout_data_empty.*
import kotlinx.android.synthetic.main.layout_error.*
import javax.inject.Inject

class StringSearchFragment : Fragment(),StringSearchContract.View {

    companion object {
        @JvmStatic
        fun newInstance() = StringSearchFragment()
    }

    @Inject
    lateinit var presenter: StringSearchContract.Presenter
    @Inject
    lateinit var model: MoviesRepository

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var scrollListener: LinearInfiniteScrollListener
    private val loadingItem = LoadingDelegateAdapter.Loading()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.instance.appComponent.fragmentComponent(FragmentModule()).inject(this)
        presenter.attach(this) { page: Int ->
            model.searchMovies(
                page.toString(),
                "ru-RU",
                "RU",
                " "
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_string_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        fragment_string_search_search_view.editText.focus(true)
        presenter.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showEmptyProgress(show: Boolean) {
        if (show) {
            fragment_string_search_recycler_view.visibility = View.GONE
            layout_progress_bar.visibility = View.VISIBLE
            scrollListener.setStartState()
        } else {
            fragment_string_search_recycler_view.visibility = View.VISIBLE
            layout_progress_bar.visibility = View.GONE
        }
    }

    override fun showEmptyError(show: Boolean) {
        if (show) {
            fragment_string_search_content.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
            fragment_string_search_search_view.editText.focus(false)
        } else {
            fragment_string_search_content.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        }
    }

    override fun showEmptyData(show: Boolean) {
        if (show) {
            fragment_string_search_recycler_view.visibility = View.GONE
            layout_data_empty.visibility = View.VISIBLE
        } else {
            fragment_string_search_recycler_view.visibility = View.VISIBLE
            layout_data_empty.visibility = View.GONE
        }
    }

    override fun showData(data: List<Movie>, swap: Boolean) {
        if (swap) {
            linearLayoutManager.scrollToPosition(0)
            (fragment_string_search_recycler_view.adapter as CompositeDelegateAdapter).swapData(data)
        } else {
            (fragment_string_search_recycler_view.adapter as CompositeDelegateAdapter).addAllData(data)
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
            fragment_string_search_content.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
            fragment_string_search_search_view.editText.focus(false)
        } else {
            fragment_string_search_content.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        }
    }

    override fun showPageProgress(show: Boolean) {
        if (show) {
            (fragment_string_search_recycler_view.adapter as CompositeDelegateAdapter).addData(loadingItem)
        } else {
            (fragment_string_search_recycler_view.adapter as CompositeDelegateAdapter).removeData(loadingItem)
        }
    }

    private fun initUI() {
        initRecyclerView()

        button_refresh.setOnClickListener {
            presenter.refresh()
        }

        fragment_string_search_search_view.setBackButtonClickListener({
            fragment_string_search_search_view.editText.focus(false)
            activity?.onBackPressed()
        })
        fragment_string_search_search_view.editText.hint = "Поиск"
        fragment_string_search_search_view.addTextChangedListener({
            presenter.changeRequestFactory { page: Int ->
                model.searchMovies(
                    page.toString(),
                    "ru-RU",
                    "RU",
                    if (it.toString().isEmpty()) " " else it.toString()
                )
            }
        })
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        scrollListener =
            object : LinearInfiniteScrollListener(linearLayoutManager, 0, { presenter.loadNextPage() }) {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == SCROLL_STATE_DRAGGING) {
                        fragment_string_search_search_view.editText.focus(false)
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
        with(fragment_string_search_recycler_view) {
            addOnScrollListener(scrollListener)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecoration(16, 4, context))
            layoutManager = linearLayoutManager
            adapter = adapterCustom
        }
    }
}
