package com.abdulmanov.MoviCorn.ui.filters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity;
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.model.Filter

import kotlinx.android.synthetic.main.activity_filters.*
import kotlinx.android.synthetic.main.item_genre.view.*
import kotlinx.android.synthetic.main.item_list_little_poster.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

class FiltersActivity : AppCompatActivity() {

    companion object {
        const val FILTER = "FILTER"
        fun newIntent(context: Context,filter:Filter): Intent {
            return Intent(context, FiltersActivity::class.java).apply {
                putExtra(FILTER,filter)
            }
        }
    }

    private lateinit var filter:Filter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
        setSupportActionBar(activity_filters_toolbar)
        initUI()
        filter = intent!!.extras!!.getParcelable(FILTER)
        setupFilter(intent?.extras?.getParcelable(FILTER) as Filter)
    }

    private fun initUI(){
        with(activity_filters_toolbar){
            setNavigationIcon(R.drawable.ic_arrow_back_white)
            setNavigationOnClickListener { onBackPressed() }
        }
        filters_years_range_seekbar.setMaxValue((SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toInt()+1).toFloat())

        filters_years_range_seekbar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            filters_years_min.text = minValue.toString()
            filters_years_max.text = maxValue.toString()
        }

        filters_vote_average_range_seekbar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            filters_vote_average_min.text = minValue.toString()
            filters_vote_average_max.text = maxValue.toString()
        }

        filters_apply.setOnClickListener {
            val genres = mutableListOf<Triple<Int,String,Boolean>>()
            for(i in filter.genres.indices){
                genres.add(Triple(filter.genres[i].first,filter.genres[i].second,filters_genres.getChildAt(i).isSelected))
            }
            setResult(Activity.RESULT_OK,Intent().apply {
                putExtra(FILTER,Filter(
                    "vote_average.desc",
                    genres,
                    filters_vote_average_min.text.toString().toDouble(),
                    filters_vote_average_max.text.toString().toDouble(),
                    filters_years_min.text.toString()+"-01-01",
                    filters_years_max.text.toString()+"-01-01"
                ))
            })
            finish()
        }
    }

    private fun setupFilter(filter: Filter){
        filter.genres.forEach {
            val view = layoutInflater.inflate(R.layout.item_genre, null)
            view.genre_name.text = it.second
            if(it.third) view.genre_name.isSelected = true
            view.genre_name.setOnClickListener { view ->
                view.isSelected = !view.isSelected
            }
            filters_genres.addView(view)
        }

        filters_vote_average_range_seekbar.setMinStartValue(filter.voteAverageGte.toFloat())
        filters_vote_average_range_seekbar.setMaxStartValue(filter.voteAverageLte.toFloat())
        filters_vote_average_range_seekbar.apply()

        filters_years_range_seekbar.setMinStartValue(filter.releaseDateGte.split("-")[0].toFloat())
        filters_years_range_seekbar.setMaxStartValue(filter.releaseDateLte.split("-")[0].toFloat())
        filters_years_range_seekbar.apply()
    }
}
