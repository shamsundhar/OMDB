package com.app.omdb.network

import com.app.omdb.responses.MovieDetailsResponse
import com.app.omdb.responses.MoviesListResponse
import retrofit2.http.*

interface OMDBApi {

    @GET(".")
    suspend fun fetchMoviesListByName(
        @Query("apiKey") apiKey : String,
        @Query("s")movieName:String
    ) : MoviesListResponse


    @GET(".")
    suspend fun fetchMovieDetailsByID(
        @Query("apiKey") apiKey : String,
        @Query("i")movieID:String
    ) : MovieDetailsResponse


}