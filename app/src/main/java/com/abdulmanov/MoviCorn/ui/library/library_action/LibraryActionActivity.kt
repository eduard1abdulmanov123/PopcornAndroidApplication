package com.abdulmanov.MoviCorn.ui.library.library_action

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulmanov.MoviCorn.BaseApp
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.adapters.LibraryActionAdapter
import com.abdulmanov.MoviCorn.di.module.ActivityModule
import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import com.abdulmanov.customviews.recyclerview.VerticalItemDecoration

import kotlinx.android.synthetic.main.activity_library_action.*
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

class LibraryActionActivity : AppCompatActivity(),LibraryActionContract.View {

    companion object{
        private const val MOVIES = "MOVIES"
        private const val POSITION = "POSITION"
        fun newIntent(context: Context,movies:List<Movie>,position:Int):Intent{
            return Intent(context,LibraryActionActivity::class.java).apply{
                putExtra(MOVIES,movies.toTypedArray())
                putExtra(POSITION,position)
            }
        }

    }

    @Inject
    lateinit var presenter: LibraryActionContract.Presenter

    private lateinit var chooseAllMenuItem:MenuItem
    private lateinit var movies:List<Movie>
    private var position: Int = -1
    private val adapter by lazy {
        LibraryActionAdapter{
            title = getString(R.string.library_action_title,it)
            changeTitleChooseAllMenuItem()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_action)
        setSupportActionBar(library_action_toolbar)
        BaseApp.instance.appComponent.activityComponent(ActivityModule(this)).inject(this)
        presenter.attach(this)
        movies = intent.extras!!.getParcelableArray(MOVIES)!!.toList() as List<Movie>
        position = intent.extras!!.getInt(POSITION)
        initUI()
        adapter.addData(movies)
        adapter.toggleSelection(position)
        title = getString(R.string.library_action_title,1)
        library_action_recycler_view.layoutManager?.scrollToPosition(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_library_action,menu)
        chooseAllMenuItem = menu!!.findItem(R.id.library_action_choose_all)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.library_action_delete -> {
                presenter.deleteMovies(adapter.getSelectedData())
                true
            }
            R.id.library_action_choose_all-> {
                if(chooseAllMenuItem.title == getString(R.string.library_action_cancel)){
                    adapter.clearSelections()
                }else {
                    adapter.selectAll()
                }
                title = getString(R.string.library_action_title,adapter.getSelectedItemCount())
                changeTitleChooseAllMenuItem()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }

    override fun showMessage(message: Int) {
        val str = baseContext.resources.getString(message)
        Snackbar.make(library_action_root_view,str,Snackbar.LENGTH_SHORT).show()
    }

    override fun callbackDelete() {
        adapter.clearSelectionsData()
        adapter.clearSelections()
        if(adapter.itemCount==0) {
            finish()
        }
        else {
            title = getString(R.string.library_action_title,adapter.getSelectedItemCount())
            showMessage(R.string.library_action_message_delete)
        }
    }

    private fun initUI(){
        library_action_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
        library_action_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        with(library_action_recycler_view){
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecoration(16,4,context))
            layoutManager = LinearLayoutManager(context)
            adapter = this@LibraryActionActivity.adapter
        }
    }

    private fun changeTitleChooseAllMenuItem(){
        if(adapter.getSelectedItemCount()==movies.size){
            chooseAllMenuItem.title = getString(R.string.library_action_cancel)
        }else {
            chooseAllMenuItem.title = getString(R.string.library_action_choose_all)
        }
    }
}
