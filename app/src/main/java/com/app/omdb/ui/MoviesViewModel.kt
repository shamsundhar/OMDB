package com.app.omdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.omdb.network.Resource
import com.app.omdb.repository.BaseRepository
import com.app.omdb.repository.MovieRepository
import com.app.omdb.responses.MovieDetailsResponse
import com.app.omdb.responses.MoviesListResponse
import kotlinx.coroutines.launch

class MoviesViewModel(
        private val  repository: MovieRepository
) : ViewModel() {

    private val _moviesListResponse : MutableLiveData<Resource<MoviesListResponse>> = MutableLiveData()
    val moviesListResponse : LiveData<Resource<MoviesListResponse>> get() = _moviesListResponse


    private val _moviesDetailsResponse : MutableLiveData<Resource<MovieDetailsResponse>> = MutableLiveData()
    val moviesDetailsResponse : LiveData<Resource<MovieDetailsResponse>> get() = _moviesDetailsResponse

    fun fetchMoviesList(
            apiKey: String,
            movieName : String
    ) = viewModelScope.launch {
        println("viewModelScope")
        _moviesListResponse.value = repository.fetchMoviesList(apiKey,movieName)
    }


    fun fetchMovieDetailsByID(
        apiKey: String,
        movieID : String
    ) = viewModelScope.launch {
        println("viewModelScope")
        _moviesDetailsResponse.value = repository.fetchMovieDetailsByID(apiKey,movieID)
    }

}