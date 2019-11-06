package com.abdulmanov.MoviCorn.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.ui.common_ui.BaseFragment
import com.abdulmanov.MoviCorn.ui.library.library_main.LibraryFragment
import com.abdulmanov.MoviCorn.ui.movie.main.MovieMainFragment
import com.abdulmanov.MoviCorn.ui.search.filter_search.SearchFilterFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener{

    private val fragments = listOf(
        BaseFragment.newInstance(R.layout.content_movie_base,  R.id.nav_host_movie,MovieMainFragment::class.qualifiedName!!),
        BaseFragment.newInstance(R.layout.content_search_base, R.id.nav_host_search,
            SearchFilterFragment::class.qualifiedName!!),
        BaseFragment.newInstance(R.layout.content_library_base, R.id.nav_host_library,
            LibraryFragment::class.qualifiedName!!))

    private val indexToPage = mapOf(0 to R.id.movie, 1 to R.id.search, 2 to R.id.library)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setup main view pager
        main_view_pager.adapter = ViewPagerAdapter()
        main_view_pager.addOnPageChangeListener(this)
        main_view_pager.offscreenPageLimit = 3
        // setup bottom navigation view
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        bottom_navigation_view.setOnNavigationItemReselectedListener(this)

    }

    override fun onBackPressed() {
        val fragment = fragments[main_view_pager.currentItem]
        if (!fragment.onBackPressed()) {
            if (indexToPage[main_view_pager.currentItem]!=R.id.movie) {
                main_view_pager.currentItem = indexToPage.values.indexOf(R.id.movie)

            } else super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        main_view_pager.currentItem = position

        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        fragments[position].popToRoot()
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        val itemId = indexToPage[position] ?: R.id.movie
        if (bottom_navigation_view.selectedItemId != itemId) bottom_navigation_view.selectedItemId = itemId
    }

    inner class ViewPagerAdapter : FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

    }
}

