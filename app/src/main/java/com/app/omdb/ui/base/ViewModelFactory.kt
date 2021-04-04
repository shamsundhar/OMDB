package com.app.omdb.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.omdb.repository.BaseRepository
import com.app.omdb.repository.MovieRepository
import com.app.omdb.ui.MoviesViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
        private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> MoviesViewModel(repository as MovieRepository) as T
            else -> throw  IllegalArgumentException("View model class not found")
        }
    }
}