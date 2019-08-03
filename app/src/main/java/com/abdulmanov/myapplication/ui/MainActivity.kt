package com.abdulmanov.myapplication.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.abdulmanov.myapplication.R
import com.abdulmanov.myapplication.common.hideFragment
import com.abdulmanov.myapplication.common.showFragment
import com.abdulmanov.myapplication.ui.library.LibraryFragment
import com.abdulmanov.myapplication.ui.movie.MovieHostFragment
import com.abdulmanov.myapplication.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.abdulmanov.myapplication.R.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        window.setBackgroundDrawable(null)
        showFragment(MovieHostFragment.MOVIE_HOST_TAG)
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            showFragment(when(it.itemId) {
                R.id.movie -> MovieHostFragment.MOVIE_HOST_TAG
                R.id.search -> SearchFragment.SEARCH_TAG
                R.id.library -> LibraryFragment.LIBRARY_TAG
                else -> MovieHostFragment.MOVIE_HOST_TAG
            })
            true
        }
    }

    private fun showFragment(showTag: String) {
        when(showTag){
            MovieHostFragment.MOVIE_HOST_TAG -> {
                supportFragmentManager.hideFragment(SearchFragment.SEARCH_TAG )
                supportFragmentManager.hideFragment(LibraryFragment.LIBRARY_TAG)
                supportFragmentManager.showFragment(showTag,
                    id.fragment_main_host,MovieHostFragment.newInstance())
            }
            SearchFragment.SEARCH_TAG ->{
                supportFragmentManager.hideFragment(MovieHostFragment.MOVIE_HOST_TAG)
                supportFragmentManager.hideFragment(LibraryFragment.LIBRARY_TAG)
                supportFragmentManager.showFragment(showTag,
                    id.fragment_main_host,SearchFragment.newInstance())
            }
            LibraryFragment.LIBRARY_TAG->{
                supportFragmentManager.hideFragment(MovieHostFragment.MOVIE_HOST_TAG)
                supportFragmentManager.hideFragment(SearchFragment.SEARCH_TAG)
                val libFragment = supportFragmentManager.findFragmentByTag(LibraryFragment.LIBRARY_TAG) as? LibraryFragment
                libFragment?.update()
                supportFragmentManager.showFragment(showTag,
                    id.fragment_main_host,LibraryFragment.newInstance())
            }
        }
    }

}

