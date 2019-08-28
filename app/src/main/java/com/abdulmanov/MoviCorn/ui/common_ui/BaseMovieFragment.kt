package com.abdulmanov.MoviCorn.ui.common_ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.core.network.model.Model
import com.abdulmanov.customviews.recyclerview.CompositeDelegateAdapter
import com.abdulmanov.customviews.recyclerview.LinearInfiniteScrollListener
import com.abdulmanov.MoviCorn.BaseApp
import com.abdulmanov.MoviCorn.di.component.FragmentComponent
import com.abdulmanov.MoviCorn.di.module.FragmentModule
import com.abdulmanov.MoviCorn.model.vo.movie.FilmMedium
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import io.reactivex.Single
import javax.inject.Inject

abstract class BaseMovieFragment : Fragment(),BaseListMovieContract.View<FilmMedium> {

    companion object {
        private val LOADING_ITEM = LoadingDelegateAdapter.Loading()
    }

    @Inject
    protected lateinit var mPresenter: BaseListMovieContract.Presenter<FilmMedium>

    @Inject
    protected lateinit var networkModel: Model

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var scrollListener: LinearInfiniteScrollListener
    protected lateinit var emptyProgressBar: View
    protected var buttonRefresh: View? = null
    protected var refreshProgressBar: View? = null
    protected var containerErrorView: View? = null
    protected  var containerEmptyData: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency(BaseApp.instance.appComponent.fragmentComponent(FragmentModule()))
        mPresenter.attach(this, getTypeFunctionForFilm())
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
        mPresenter.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
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
            containerErrorView?.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            containerErrorView?.visibility = View.GONE
        }
    }

    override fun showEmptyData(show: Boolean) {
        Log.d("MoviePaginator", "showEmptyData $show")
        if (show) {
            recyclerView.visibility = View.GONE
            containerEmptyData?.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            containerEmptyData?.visibility = View.GONE
        }
    }

    override fun showFailProgress(show: Boolean) {
        Log.d("MoviePaginator", "showFailProgress $show")
        if (show) {
            buttonRefresh?.visibility = View.INVISIBLE
            refreshProgressBar?.visibility = View.VISIBLE
        } else {
            buttonRefresh?.visibility = View.VISIBLE
            refreshProgressBar?.visibility = View.INVISIBLE
        }
    }

    override fun showData(data: List<FilmMedium>, swap:Boolean) {
        Log.d("MoviePaginator", "showData($swap)")
        if(swap){
            (recyclerView.adapter as CompositeDelegateAdapter).swapData(data)
            recyclerView.layoutManager!!.scrollToPosition(0)
        }else{
            (recyclerView.adapter as CompositeDelegateAdapter).addAllData(data)
        }
    }


    override fun showErrorMessage(show: Boolean) {
        Log.d("MoviePaginator", "showError $show")
        if (show) {
            recyclerView.visibility = View.GONE
            containerErrorView?.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            containerErrorView?.visibility = View.GONE
        }
    }

    override fun showPageProgress(show: Boolean) {
        Log.d("MoviePaginator", "showPageProgress $show")
        if (show) {
            (recyclerView.adapter as CompositeDelegateAdapter).addData(LOADING_ITEM)
        } else {
            (recyclerView.adapter as CompositeDelegateAdapter).removeData(LOADING_ITEM)
        }
    }

    private fun initViewFragment(view: View){
        recyclerView = installRecycleView(view)
        buttonRefresh = installRefreshButton(view)
        emptyProgressBar = installEmptyProgressBar(view)
        refreshProgressBar = installRefreshProgressBar(view)
        containerErrorView = installContainerErrorView(view)
        containerEmptyData = containerEmptyData(view)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(context)
        initScrollListener(linearLayoutManager)
        val movieDelegateAdapter = MovieDelegateAdapter(getTypeLayoutForFilm()){
            startActivity(DetailsMovieActivity.newIntent(context!!,it))
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
            LinearInfiniteScrollListener(linearLayoutManager, 0, { mPresenter.loadNextPage() })
    }

    private fun initContainerErrorView() {
        buttonRefresh?.setOnClickListener { mPresenter.refresh() }
    }

    abstract fun installRecycleView(view: View):RecyclerView
    abstract fun installRefreshButton(view: View):View?
    abstract fun installEmptyProgressBar(view: View): View
    abstract fun installRefreshProgressBar(view: View): View?
    abstract fun installContainerErrorView(view: View): View?
    abstract fun containerEmptyData(view: View): View?
    abstract fun injectDependency(fragmentComponent: FragmentComponent)
    abstract fun getTypeFunctionForFilm(): (page: Int) -> Single<MoviesDTO>
    abstract fun getTypeLayoutForFragment(): Int
    abstract fun getTypeLayoutForFilm(): Int
}