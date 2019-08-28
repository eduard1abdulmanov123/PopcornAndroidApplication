package com.abdulmanov.MoviCorn.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.hideFragment
import com.abdulmanov.MoviCorn.common.showFragment
import com.abdulmanov.MoviCorn.ui.library.LibraryFragment
import com.abdulmanov.MoviCorn.ui.movie.MovieHostFragment
import com.abdulmanov.MoviCorn.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.abdulmanov.MoviCorn.R.*


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

