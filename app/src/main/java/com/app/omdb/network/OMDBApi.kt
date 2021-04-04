package com.app.omdb.network

import com.app.omdb.responses.MovieDetailsResponse
import com.app.omdb.responses.MoviesListResponse
import com.app.omdb.ui.common.Constants.Companion.QUERY_KEY_PARAM_API_KEY
import com.app.omdb.ui.common.Constants.Companion.QUERY_KEY_PARAM_I
import com.app.omdb.ui.common.Constants.Companion.QUERY_KEY_PARAM_S
import retrofit2.http.*

interface OMDBApi {

    @GET(".")
    suspend fun fetchMoviesListByName(
        @Query(QUERY_KEY_PARAM_API_KEY) apiKey: String,
        @Query(QUERY_KEY_PARAM_S) movieName: String
    ): MoviesListResponse


    @GET(".")
    suspend fun fetchMovieDetailsByID(
        @Query(QUERY_KEY_PARAM_API_KEY) apiKey: String,
        @Query(QUERY_KEY_PARAM_I) movieID: String
    ): MovieDetailsResponse


}