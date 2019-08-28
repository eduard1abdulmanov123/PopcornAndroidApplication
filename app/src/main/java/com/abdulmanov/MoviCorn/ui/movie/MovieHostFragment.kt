package com.abdulmanov.MoviCorn.ui.movie

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.hideFragment
import com.abdulmanov.MoviCorn.common.showFragment
import kotlinx.android.synthetic.main.fragment_movie_host.*


class MovieHostFragment : Fragment() {

    companion object {
        const val MOVIE_HOST_TAG = "MOVIE_HOST_TAG"
        @JvmStatic
        fun newInstance() = MovieHostFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_host, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showFragment(MovieFragment.NOW_PLAYING_TAG)
        tab_layout.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(p0: TabLayout.Tab?) {
                Log.d("Tab_layout",p0!!.position.toString())
                when (tab_layout.selectedTabPosition) {
                    0 -> showFragment(MovieFragment.NOW_PLAYING_TAG)
                    1 -> showFragment(MovieFragment.UPCOMING_TAG)
                    2 -> showFragment(MovieFragment.TOP_RATED_TAG)
                }

            }
        })
    }

    private fun showFragment(showTag: String) {
        when(showTag){
            MovieFragment.NOW_PLAYING_TAG -> {
                childFragmentManager.hideFragment(MovieFragment.UPCOMING_TAG)
                childFragmentManager.hideFragment(MovieFragment.TOP_RATED_TAG)
            }
            MovieFragment.UPCOMING_TAG ->{
                childFragmentManager.hideFragment(MovieFragment.NOW_PLAYING_TAG)
                childFragmentManager.hideFragment(MovieFragment.TOP_RATED_TAG)
            }
            MovieFragment.TOP_RATED_TAG->{
                childFragmentManager.hideFragment(MovieFragment.NOW_PLAYING_TAG)
                childFragmentManager.hideFragment(MovieFragment.UPCOMING_TAG)
            }

        }
        childFragmentManager.showFragment(showTag,R.id.fragment_movie_host,MovieFragment.newInstance())
    }

}
