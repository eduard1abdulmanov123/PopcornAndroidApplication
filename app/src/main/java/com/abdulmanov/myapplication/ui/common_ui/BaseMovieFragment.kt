package com.abdulmanov.myapplication.ui.common_ui

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.core.network.model.Model
import com.abdulmanov.customviews.recyclerview.CompositeDelegateAdapter
import com.abdulmanov.customviews.recyclerview.LinearInfiniteScrollListener
import com.abdulmanov.myapplication.BaseApp
import com.abdulmanov.myapplication.di.component.FragmentComponent
import com.abdulmanov.myapplication.di.module.FragmentModule
import com.abdulmanov.myapplication.model.vo.FilmShort
import com.abdulmanov.myapplication.ui.details.DetailsActivity
import com.github.nikartm.button.FitButton
import com.wang.avi.AVLoadingIndicatorView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

abstract class BaseMovieFragment : Fragment(),BaseMovieContract.View<FilmShort> {

    companion object {
        private val LOADING_ITEM = LoadingDelegateAdapter.Loading()
    }

    @Inject
    protected lateinit var presenter: BaseMovieContract.Presenter<FilmShort>

    @Inject
    protected lateinit var networkModel: Model

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var buttonRefresh: FitButton
    protected lateinit var emptyProgressBar: AVLoadingIndicatorView
    protected lateinit var refreshProgressBar: AVLoadingIndicatorView
    protected lateinit var containerErrorView: ConstraintLayout
    protected lateinit var containerEmptyData: TextView
    protected lateinit var scrollListener: LinearInfiniteScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency(BaseApp.instance.appComponent.fragmentComponent(FragmentModule()))
        presenter.attach(this, getTypeFunctionForFilm())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getTypeLayoutForFragment(), container, false)
        initViewFragment(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView(recyclerView)
        initContainerErrorView()
        presenter.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showEmptyProgress(show: Boolean) {
        Log.d("MoviePaginator", "showEmptyProgress $show")
        if (show) {
            recyclerView.visibility = View.GONE
            emptyProgressBar.visibility = View.VISIBLE
            scrollListener.setStartState()
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyProgressBar.visibility = View.GONE
        }
    }

    override fun showEmptyError(show: Boolean) {
        Log.d("MoviePaginator", "showEmptyError $show")
        if (show) {
            recyclerView.visibility = View.GONE
            containerErrorView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            containerErrorView.visibility = View.GONE
        }
    }

    override fun showEmptyData(show: Boolean) {
        Log.d("MoviePaginator", "showEmptyData $show")
        if (show) {
            recyclerView.visibility = View.GONE
            containerEmptyData.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            containerEmptyData.visibility = View.GONE
        }
    }

    override fun showFailProgress(show: Boolean) {
        Log.d("MoviePaginator", "showFailProgress $show")
        if (show) {
            buttonRefresh.visibility = View.INVISIBLE
            refreshProgressBar.visibility = View.VISIBLE
        } else {
            buttonRefresh.visibility = View.VISIBLE
            refreshProgressBar.visibility = View.INVISIBLE
        }
    }

    override fun showData(data: List<FilmShort>,swap:Boolean) {
        Log.d("MoviePaginator", "showData($swap)")
        if(swap){
            (recyclerView.adapter as CompositeDelegateAdapter).swapData(data)
            recyclerView.layoutManager!!.scrollToPosition(0)
        }else{
            (recyclerView.adapter as CompositeDelegateAdapter).addAllData(data)
        }
    }


    override fun showErrorMessage(show: Boolean) {
        Log.d("MoviePaginator", "showErrorMessage $show")
        if (show) {
            recyclerView.visibility = View.GONE
            containerErrorView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            containerErrorView.visibility = View.GONE
        }
    }

    override fun showPageProgress(show: Boolean) {
        Log.d("MoviePaginator", "showPageProgress $show")
        if (show) {
            (recyclerView.adapter as CompositeDelegateAdapter).addData(BaseMovieFragment.LOADING_ITEM)
        } else {
            (recyclerView.adapter as CompositeDelegateAdapter).removeData(BaseMovieFragment.LOADING_ITEM)
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(context)
        initScrollListener(linearLayoutManager)
        val movieDelegateAdapter = MovieDelegateAdapter(getTypeLayoutForFilm()){
            startActivity(DetailsActivity.newIntent(context!!,it))
        }
        val adapterCustom = CompositeDelegateAdapter.Builder()
            .add(LoadingDelegateAdapter())
            .add(movieDelegateAdapter)
            .build()
        with(recyclerView) {
            addOnScrollListener(scrollListener)
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = adapterCustom
        }
    }

    open fun initScrollListener(linearLayoutManager: LinearLayoutManager) {
        scrollListener  =
            LinearInfiniteScrollListener(linearLayoutManager, 0, { presenter.loadNextPage() })
    }

    private fun initContainerErrorView() {
        buttonRefresh.setOnClickListener { presenter.refresh() }
    }

    abstract fun initViewFragment(view: View)
    abstract fun injectDependency(fragmentComponent: FragmentComponent)
    abstract fun getTypeFunctionForFilm(): (page: Int) -> Observable<MoviesDTO>
    abstract fun getTypeLayoutForFragment(): Int
    abstract fun getTypeLayoutForFilm(): Int
}