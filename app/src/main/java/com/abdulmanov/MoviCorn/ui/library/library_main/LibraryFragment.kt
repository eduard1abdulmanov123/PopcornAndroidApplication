package com.abdulmanov.MoviCorn.ui.library.library_main


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
import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import com.abdulmanov.MoviCorn.ui.library.library_action.LibraryActionActivity
import com.abdulmanov.customviews.recyclerview.CompositeDelegateAdapter
import com.abdulmanov.customviews.recyclerview.VerticalItemDecoration
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.layout_library_empty.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import javax.inject.Inject


class LibraryFragment : Fragment(), LibraryContract.View{

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
    lateinit var presenter: LibraryContract.Presenter

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var movies:List<Movie>
    private var showBackToTop = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.instance.appComponent.fragmentComponent(FragmentModule()).inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        library_toolbar.title = getString(R.string.library)
        initUI()
        presenter.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showProgress(show: Boolean) {
        if(show){
            library_recycler_view.visibility = View.GONE
            library_back_to_top.visibility = View.GONE
            layout_progress_bar.visibility = View.VISIBLE
        }else{
            library_recycler_view.visibility = View.VISIBLE
            library_back_to_top.visibility = View.VISIBLE
            layout_progress_bar.visibility = View.GONE
        }
    }

    override fun showEmptyData(show:Boolean) {
        if(show) {
            library_recycler_view.visibility = View.GONE
            library_back_to_top.visibility = View.GONE
            layout_library_empty.visibility = View.VISIBLE
        }else{
            library_recycler_view.visibility = View.VISIBLE
            library_back_to_top.visibility = View.VISIBLE
            layout_library_empty.visibility = View.GONE
        }
    }

    override fun showData(movies: List<Movie>) {
        this.movies = movies
        Log.d("LibraryTest",movies.toString())
        (library_recycler_view.adapter as CompositeDelegateAdapter).swapData(movies)
    }

    private fun initUI(){
        initRecyclerViewAndFAB()
        library_back_to_top.setOnClickListener {
            linearLayoutManager.scrollToPosition(0)
            library_recycler_view.stopScroll()
            library_app_bar.setExpanded(true,false)
        }
    }

    private fun initRecyclerViewAndFAB() {
        linearLayoutManager = LinearLayoutManager(context)
        val scrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (linearLayoutManager.findFirstVisibleItemPosition() == 0 && showBackToTop) {
                        library_back_to_top.hide()
                        showBackToTop = false
                    } else if (linearLayoutManager.findFirstVisibleItemPosition() != 0 && !showBackToTop) {
                        library_back_to_top.show()
                        showBackToTop = true
                    }
                }
            }
        val movieDelegateAdapter =
            MovieDelegateAdapter (
                { startActivity(DetailsMovieActivity.newIntent(context!!, it)) },
                { startActivity(LibraryActionActivity.newIntent(context!!,movies,it))}
            )
        val adapterCustom = CompositeDelegateAdapter.Builder()
            .add(movieDelegateAdapter)
            .build()
        with(library_recycler_view) {
            addOnScrollListener(scrollListener)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecoration(16,4,context))
            layoutManager = linearLayoutManager
            adapter = adapterCustom
        }
    }
}
