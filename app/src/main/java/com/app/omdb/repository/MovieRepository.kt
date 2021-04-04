package com.app.omdb.repository

import com.app.omdb.network.OMDBApi

class MovieRepository(private val api : OMDBApi) : BaseRepository(){

    suspend fun fetchMoviesList(
        apiKey: String,
        movieName: String
    ) = safeApiCall {
        api.fetchMoviesListByName(apiKey,movieName)
    }


    suspend fun fetchMovieDetailsByID(
        apiKey: String,
        movieID: String
    ) = safeApiCall {
        api.fetchMovieDetailsByID(apiKey,movieID)
    }

}