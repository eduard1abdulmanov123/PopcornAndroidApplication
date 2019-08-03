package com.abdulmanov.myapplication.ui.library


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.core.database.model.Model
import com.abdulmanov.customviews.recyclerview.CompositeDelegateAdapter
import com.abdulmanov.customviews.recyclerview.LinearInfiniteScrollListener
import com.abdulmanov.myapplication.BaseApp
import com.abdulmanov.myapplication.R
import com.abdulmanov.myapplication.di.module.FragmentModule
import com.abdulmanov.myapplication.model.mappers.FilmLibraryMapper
import com.abdulmanov.myapplication.model.vo.FilmLibrary
import com.abdulmanov.myapplication.ui.common_ui.BaseMovieFragment
import com.abdulmanov.myapplication.ui.common_ui.LoadingDelegateAdapter
import com.abdulmanov.myapplication.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.empty_progress_bar.*
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject


class LibraryFragment : Fragment(),LibraryContract.View<FilmLibrary> {

    companion object {
        const val LIBRARY_TAG= "LIBRARY_TAG"
        private val LOADING_ITEM = LoadingDelegateAdapter.Loading()
        @JvmStatic
        fun newInstance() =
            LibraryFragment().apply {
                arguments = Bundle().apply {}
            }
    }
    @Inject
    lateinit var database:Model
    @Inject
    lateinit var presenter:LibraryContract.Presenter<FilmLibrary>

    private lateinit var scrollListener:LinearInfiniteScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.instance.appComponent.fragmentComponent(FragmentModule()).inject(this)
        presenter.attach(this) { page:Int->database.getMoviesSortTitle(page)}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        presenter.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showEmptyProgress(show: Boolean) {
        if (show) {
            library_recycler_view.visibility = View.GONE
            empty_progress_bar.visibility = View.VISIBLE
            scrollListener.setStartState()
        } else {
            library_recycler_view.visibility = View.VISIBLE
            empty_progress_bar.visibility = View.GONE
        }
    }

    override fun showEmptyData(show: Boolean) {
        if (show) {
            library_recycler_view.visibility = View.GONE
            library_empty_data.visibility = View.VISIBLE
        } else {
            library_recycler_view.visibility = View.VISIBLE
            library_empty_data.visibility = View.GONE
        }
    }

    override fun showData(data: List<FilmLibrary>, swap: Boolean) {
        val adapter = (library_recycler_view.adapter as CompositeDelegateAdapter)
        if(swap){
            adapter.swapData(data)
            library_recycler_view.layoutManager!!.scrollToPosition(0)
        }else{
           adapter.addAllData(data)
        }
    }

    override fun showPageProgress(show: Boolean) {
        if (show) {
            (library_recycler_view.adapter as CompositeDelegateAdapter).addData(LOADING_ITEM)
        } else {
            (library_recycler_view.adapter as CompositeDelegateAdapter).removeData(LOADING_ITEM)
        }
    }

    override fun showDeleteFilm(film: FilmLibrary) {
        val adapter = (library_recycler_view.adapter as CompositeDelegateAdapter)
        adapter.removeData(film)
        Log.d("DBMOVIE","adapter=${adapter.itemCount}")
        if(adapter.itemCount==0)
            showEmptyData(true)
        else if(adapter.itemCount<10)
            presenter.loadNewPage()
    }

    private fun initRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(context)
        scrollListener = LinearInfiniteScrollListener(linearLayoutManager,0,{presenter.loadNewPage()})
        val movieAdapter = MovieLibraryDelegateAdapter(
            {startActivity(DetailsActivity.newIntent(context!!,it))},
            { presenter.deleteFilm(database,it) }
        )
        val adapterCustom = CompositeDelegateAdapter.Builder()
            .add(LoadingDelegateAdapter())
            .add(movieAdapter)
            .build()
        with(library_recycler_view){
            addOnScrollListener(scrollListener)
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = adapterCustom
        }
    }

    fun update(){
        showEmptyData(false)
        presenter.refresh()
    }
}
