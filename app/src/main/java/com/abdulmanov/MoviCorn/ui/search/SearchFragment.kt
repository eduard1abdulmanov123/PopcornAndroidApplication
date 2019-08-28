package com.abdulmanov.MoviCorn.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.customviews.recyclerview.LinearInfiniteScrollListener
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.focus
import com.abdulmanov.MoviCorn.common.hideKeyboard
import com.abdulmanov.MoviCorn.di.component.FragmentComponent
import com.abdulmanov.MoviCorn.ui.common_ui.BaseMovieFragment
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.android.synthetic.main.content_empty_progress_bar.view.*
import kotlinx.android.synthetic.main.content_error.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : BaseMovieFragment(),SearchContract.View {

    companion object {
        const val SEARCH_TAG = "SEARCH_TAG"
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearchView()
    }

    override fun initScrollListener(linearLayoutManager: LinearLayoutManager) {
        scrollListener = LinearInfiniteScrollListener(
            linearLayoutManager,
            0,
            {
                mPresenter.loadNextPage()
            },
            {
                search_view.editText.focus(false)
            }
        )
    }

    override fun installRecycleView(view: View): RecyclerView = view.recycler_view_search

    override fun installRefreshButton(view: View): View? = view.button_refresh

    override fun installEmptyProgressBar(view: View): View = view.empty_progress_bar

    override fun installRefreshProgressBar(view: View): View? = view.refresh_progress_bar

    override fun installContainerErrorView(view: View): View? = view.container_error

    override fun containerEmptyData(view: View): View? = view.empty_data

    override fun injectDependency(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getTypeFunctionForFilm(): (page: Int) -> Single<MoviesDTO> {
        return { page: Int -> networkModel.getPopularMovies(page.toString(), "ru-RU", "RU")}
    }

    override fun getTypeLayoutForFragment() = R.layout.fragment_search

    override fun getTypeLayoutForFilm() = R.layout.item_list_little_poster

    override fun showEmptyError(show: Boolean) {
        super.showEmptyError(show)
        showError(show)
    }

    override fun showErrorMessage(show: Boolean) {
        super.showErrorMessage(show)
        showError(show)
    }

    override fun performSearch(key: String?) {
        if (key != null) {
            mPresenter.changeRequestFactory { page: Int ->
                networkModel.getSearchMovies(
                    page.toString(),
                    "ru-RU",
                    "RU",
                    key
                )
            }
        } else {
            mPresenter.changeRequestFactory { page: Int ->
                networkModel.getPopularMovies(
                    page.toString(),
                    "ru-RU",
                    "RU"
                )
            }
        }
    }

    private fun showError(show: Boolean) {
        if (show) {
            app_bar.visibility = View.INVISIBLE
            context!!.hideKeyboard(search_view)
        } else {
            app_bar.visibility = View.VISIBLE
        }
    }

    private fun initSearchView() {
        search_view.addTextChangedListener(
            {
                if(it.toString().isEmpty()){
                    performSearch(" ")
                } else{
                    performSearch(it.toString())
                }
            }
        )
        search_view.setBackButtonClickListener {
            performSearch(null)
        }

    }

}
