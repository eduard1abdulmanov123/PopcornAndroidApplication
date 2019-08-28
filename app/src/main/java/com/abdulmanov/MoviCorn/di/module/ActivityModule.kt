package com.abdulmanov.MoviCorn.di.module

import android.content.Context
import com.abdulmanov.core.network.model.Model as NetModel
import com.abdulmanov.core.database.model.Model as DBModel
import com.abdulmanov.MoviCorn.di.scope.ActivityScope
import com.abdulmanov.MoviCorn.model.mappers.movies.ListCreditsDTOtoListMovieCreditMapper
import com.abdulmanov.MoviCorn.model.mappers.movies.ListVideosDTOtoListMovieVideoMapper
import com.abdulmanov.MoviCorn.model.mappers.movies.MoviesDTOtoListFilmLittleMapper
import com.abdulmanov.MoviCorn.model.mappers.movies.MoviesDetailsDTOtoDetailsMovieMapper
import com.abdulmanov.MoviCorn.model.mappers.person.PeopleDetailsDTOtoDetailsPersonMapper
import com.abdulmanov.MoviCorn.model.mappers.person.PeopleMovieCastDTOtoFilmLittleMapper
import com.abdulmanov.MoviCorn.model.mappers.person.PeopleMovieCrewDTOtoFilmLittleMapper
import com.abdulmanov.MoviCorn.ui.details_person.DetailsPersonContract
import com.abdulmanov.MoviCorn.ui.details_person.DetailsPersonPresenter
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieContract
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMoviePresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val context: Context) {

    /*Movie details*/
    @ActivityScope
    @Provides
    fun provideDetailsMoviePresenter(
        network: NetModel,
        database: DBModel,
        movieMapper: MoviesDetailsDTOtoDetailsMovieMapper
    ): DetailsMovieContract.Presenter {
        return DetailsMoviePresenter(network, database, movieMapper)
    }

    @ActivityScope
    @Provides
    fun provideMoviesDetailsDTOtoDetailsMovieMapper(
        listCreditsDTOtoListMovieCreditMapper: ListCreditsDTOtoListMovieCreditMapper,
        listVideosDTOtoListMovieVideoMapper: ListVideosDTOtoListMovieVideoMapper,
        moviesDTOtoListFilmLittleMapper: MoviesDTOtoListFilmLittleMapper
    ): MoviesDetailsDTOtoDetailsMovieMapper {
        return MoviesDetailsDTOtoDetailsMovieMapper(
            context,
            listCreditsDTOtoListMovieCreditMapper,
            listVideosDTOtoListMovieVideoMapper,
            moviesDTOtoListFilmLittleMapper
        )
    }

    @ActivityScope
    @Provides
    fun provideListCreditsDTOtoListMovieCreditMapper():ListCreditsDTOtoListMovieCreditMapper{
        return ListCreditsDTOtoListMovieCreditMapper()
    }

    @ActivityScope
    @Provides
    fun provideListVideoDTOtoListMovieVideoMapper():ListVideosDTOtoListMovieVideoMapper{
        return ListVideosDTOtoListMovieVideoMapper()
    }

    @ActivityScope
    @Provides
    fun provideMoviesDTOtoListFilmLittleMapper():MoviesDTOtoListFilmLittleMapper{
        return MoviesDTOtoListFilmLittleMapper()
    }

    /*Person details*/
    @ActivityScope
    @Provides
    fun provideDetailsPeoplePresenter(
        network: NetModel,
        detailsPersonMapper: PeopleDetailsDTOtoDetailsPersonMapper
    ): DetailsPersonContract.Presenter {
        return DetailsPersonPresenter(network, detailsPersonMapper)
    }

    @ActivityScope
    @Provides
    fun providePeopleDetailsDTOtoDetailsPersonMapper(
        castMapper: PeopleMovieCastDTOtoFilmLittleMapper,
        crewMapper: PeopleMovieCrewDTOtoFilmLittleMapper
    ): PeopleDetailsDTOtoDetailsPersonMapper {
        return PeopleDetailsDTOtoDetailsPersonMapper(context, castMapper, crewMapper)
    }

    @ActivityScope
    @Provides
    fun providePeopleMovieCastDTOtoFilmLitterMapper(): PeopleMovieCastDTOtoFilmLittleMapper {
        return PeopleMovieCastDTOtoFilmLittleMapper()
    }

    @ActivityScope
    @Provides
    fun providePeopleMovieCrewDTOtoFilmLittleMapper(): PeopleMovieCrewDTOtoFilmLittleMapper {
        return PeopleMovieCrewDTOtoFilmLittleMapper()
    }
}