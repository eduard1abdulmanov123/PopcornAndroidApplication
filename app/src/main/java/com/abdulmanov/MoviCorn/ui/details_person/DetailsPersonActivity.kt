package com.abdulmanov.MoviCorn.ui.details_person

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import com.abdulmanov.MoviCorn.BaseApp
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.adapters.FilmLittleAdapter
import com.abdulmanov.MoviCorn.common.initHorizontalRecyclerView
import com.abdulmanov.MoviCorn.common.loadImg
import com.abdulmanov.MoviCorn.di.module.ActivityModule
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import com.abdulmanov.domain.models.people.PeopleDetails
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_details_person.*
import kotlinx.android.synthetic.main.content_details_person.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.android.synthetic.main.layout_error.*
import com.squareup.picasso.Callback
import java.lang.Exception
import javax.inject.Inject

class DetailsPersonActivity : AppCompatActivity(),DetailsPersonContract.View {

    companion object {
        private const val EXTRA_PERSON_ID = "PERSON_ID"
        fun newIntent(context: Context, id: Long): Intent {
            return Intent(context, DetailsPersonActivity::class.java).apply {
                putExtra(EXTRA_PERSON_ID, id)
            }
        }
    }

    @Inject
    lateinit var presenter:DetailsPersonContract.Presenter
    lateinit var personData:PeopleDetails
    private var personID:Long?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_person)
        setSupportActionBar(details_person_toolbar)
        intent.extras?.let {
            personID = it.getLong(EXTRA_PERSON_ID)
        }
        initUI()
        BaseApp.instance.appComponent.activityComponent(ActivityModule(this)).inject(this)
        presenter.attach(this)
        presenter.loadData(personID!!,"ru-RU")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details_person,menu)
        return true
    }

   override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.details_person_send -> {
                val intentSend = Intent(Intent.ACTION_SEND)
                intentSend.type = "text/plain"
                intentSend.putExtra(Intent.EXTRA_TEXT, personData.externalPersonIDs.theMovieDbId)
                startActivity(intentSend)
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }


    override fun showEmptyProgress(show:Boolean){
        if(show){
            container_details_person.visibility = View.GONE
            layout_progress_bar.visibility = View.VISIBLE
        } else{
            container_details_person.visibility = View.VISIBLE
            layout_progress_bar.visibility = View.GONE
        }
    }

    override fun showRefreshProgress(show: Boolean){
        if (show) {
            button_refresh.visibility = View.INVISIBLE
            refresh_progress_bar.visibility = View.VISIBLE
        } else {
            button_refresh.visibility = View.VISIBLE
            refresh_progress_bar.visibility = View.INVISIBLE
        }
    }

    override fun showError(show:Boolean,error:Throwable?){
        if(show){
            container_details_person.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
        } else{
            container_details_person.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        }
    }

    override fun showData(data: PeopleDetails){
        personData = data
        details_person_title.text = data.name
        if(data.profilePath!=null) {
            details_person_profile_image.loadImg(
                data.profilePath,
                R.drawable.placeholder_image,
                callback = object : Callback{
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        details_person_profile_image.loadImg(R.drawable.error_loading_image)
                    }

                }
            )
        }else{
            details_person_profile_image.loadImg(R.drawable.not_profile_image)
        }

        data.placeOfBirth?.let {
            container_place_birthday.visibility = View.VISIBLE
            details_person_place_birthday.text = it
        }

        data.biography?.let {
            container_biography.visibility = View.VISIBLE
            details_person_biography.text = it
        }

        details_person_gender.text = getGender(data.gender)

        with(data.externalPersonIDs){
            addExternalId(external_id_themoviedb,theMovieDbId)
            addExternalId(external_id_imdb,imdbId)
            addExternalId(external_id_facebook,facebookId)
            addExternalId(external_id_twitter,twitterId)
            addExternalId(external_id_instagram,instagramId)
        }

        if(data.filmographyPerson.cast.isNotEmpty()){
            container_cast_movies.visibility = View.VISIBLE
            (recycler_view_cast_movies.adapter as FilmLittleAdapter).add(data.filmographyPerson.cast)
        }

        if(data.filmographyPerson.crew.isNotEmpty()){
            container_crew_movies.visibility = View.VISIBLE
            (recycler_view_crew_movies.adapter as FilmLittleAdapter).add(data.filmographyPerson.crew)
        }
    }

    private fun initUI(){
        window.setBackgroundDrawable(null)
        title = ""
        details_person_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
        details_person_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        details_person_app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(appBarLayout.totalScrollRange+verticalOffset==0){
                details_person_toolbar_layout.title = personData.name
            }else{
                details_person_toolbar_layout.title = ""
            }
        })


        details_person_root_view.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                details_person_root_view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                details_person_toolbar_layout.layoutParams.height = (0.68*details_person_root_view.height).toInt()
            }
        })

        button_refresh.setOnClickListener {
            presenter.refresh(personID!!,"ru-RU")
        }

        biography_read_more.setOnClickListener {
            if(details_person_biography.maxLines == Integer.MAX_VALUE){
                details_person_biography.maxLines = 7
                biography_read_more.text = baseContext.getString(R.string.read_more)
            }else {
                details_person_biography.maxLines = Integer.MAX_VALUE
                biography_read_more.text = baseContext.getString(R.string.details_credit_read_less)
            }
        }

        recycler_view_cast_movies.initHorizontalRecyclerView(
            FilmLittleAdapter{
                startActivity(DetailsMovieActivity.newIntent(baseContext,it))
            }
        )

        recycler_view_crew_movies.initHorizontalRecyclerView(
            FilmLittleAdapter{
                startActivity(DetailsMovieActivity.newIntent(baseContext,it))
            }
        )
    }

    private fun addExternalId(view:View,url:String?){
        if(url!=null){
            view.visibility = View.VISIBLE
            view.setOnClickListener {
                val address = Uri.parse(url)
                startActivity(Intent(Intent.ACTION_VIEW,address))
            }
        }
    }

    private fun getGender(gender:Int):String{
        Log.d("CreditDetailsView", gender.toString())
        return when(gender){
            2 -> getString(R.string.man)
            1 -> getString(R.string.woman)
            0 -> getString(R.string.not_specified)
            else ->getString(R.string.not_specified)
        }
    }

}
