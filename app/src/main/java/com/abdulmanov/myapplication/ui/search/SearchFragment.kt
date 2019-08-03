package com.abdulmanov.myapplication.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.customviews.recyclerview.LinearInfiniteScrollListener
import com.abdulmanov.myapplication.R
import com.abdulmanov.myapplication.common.focus
import com.abdulmanov.myapplication.common.hideKeyboard
import com.abdulmanov.myapplication.di.component.FragmentComponent
import com.abdulmanov.myapplication.ui.common_ui.BaseMovieFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.empty_progress_bar.view.*
import kotlinx.android.synthetic.main.error_container.view.*
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
            { presenter.loadNextPage() },
            {
                search_view_edit_text.focus(false)
            })
    }

    override fun initViewFragment(view: View) {
        buttonRefresh = view.button_refresh
        refreshProgressBar = view.refresh_progress_bar
        emptyProgressBar = view.empty_progress_bar
        containerErrorView = view.container_error
        recyclerView = view.recycler_view_search
        containerEmptyData = view.empty_data
    }

    override fun injectDependency(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getTypeFunctionForFilm(): (page: Int) -> Observable<MoviesDTO> {
        return { page: Int -> networkModel.getPopularMovies(page.toString(), "ru-RU", "RU") }
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

    override fun showSearchView(show: Boolean) {
        if (show) {
            search_view_ic_search.visibility = View.INVISIBLE
            search_view_ic_back.visibility = View.VISIBLE
        } else {
            search_view_ic_search.visibility = View.VISIBLE
            search_view_ic_back.visibility = View.INVISIBLE
            search_view_edit_text.text.clear()

        }
    }

    override fun performSearch(key: String?) {
        if (key != null) {
            presenter.setFunc { page: Int ->
                networkModel.getSearchMovies(
                    page.toString(),
                    "ru-RU",
                    "RU",
                    key
                )
            }
        } else {
            presenter.setFunc { page: Int ->
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
            search_view.visibility = View.INVISIBLE
            context!!.hideKeyboard(search_view)
        } else {
            search_view.visibility = View.VISIBLE
        }
    }

    private fun initSearchView() {
        search_view_edit_text.setOnFocusChangeListener { _, focus ->
            if (focus) {
                showSearchView(true)
            }
        }

        search_view_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search_view_edit_text.focus(false)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        search_view_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isEmpty()) {
                    performSearch(" ")
                } else {
                    performSearch(p0.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    search_view_ic_clear.visibility = View.INVISIBLE
                } else {
                    search_view_ic_clear.visibility = View.VISIBLE
                }
            }
        })

        search_view_ic_search.setOnClickListener {
            search_view_edit_text.focus(true)
        }

        search_view_ic_back.setOnClickListener {
            search_view_edit_text.focus(false)
            showSearchView(false)
            performSearch(null)
        }

        search_view_ic_clear.setOnClickListener {
            search_view_edit_text.text.clear()
        }
    }

}
