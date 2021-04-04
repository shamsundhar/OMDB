package com.app.omdb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.omdb.R
import com.app.omdb.databinding.FragmentMoviesListBinding
import com.app.omdb.network.OMDBApi
import com.app.omdb.network.Resource
import com.app.omdb.repository.MovieRepository
import com.app.omdb.responses.Search
import com.app.omdb.ui.adapter.MovieListAdapter
import com.app.omdb.ui.base.BaseFragment
import com.app.omdb.ui.common.Constants.Companion.API_KEY
import com.app.omdb.ui.common.Constants.Companion.BUNDLE_KEY_MOVIE_ID

class MoviesListFragment :
    BaseFragment<MoviesViewModel, FragmentMoviesListBinding, MovieRepository>(),
    FragmentsCommunicator {

    private lateinit var moviesListAdapter: MovieListAdapter
    private var moviesList: ArrayList<Search> = arrayListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.moviesListResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    moviesList = it.value.Search as ArrayList<Search>
                    moviesListAdapter.setNewData(moviesList)
                }
                is Resource.Failure -> {
                }
            }
        })


        moviesListAdapter = MovieListAdapter(moviesList, context, this)

        binding.moviesListRc.layoutManager = LinearLayoutManager(context)
        binding.moviesListRc.adapter = moviesListAdapter
        if (viewModel != null && viewModel.moviesListResponse.value == null) {
            viewModel.fetchMoviesList(API_KEY, "Frozen")
        }
    }

    override fun getViewModel(): Class<MoviesViewModel> = MoviesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoviesListBinding = FragmentMoviesListBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): MovieRepository = MovieRepository(
        remoteDataSource.buildApi(OMDBApi::class.java)
    )

    override fun onRecyclerViewClicked(movieID: String) {
        val bundle = Bundle()
        bundle.putString(BUNDLE_KEY_MOVIE_ID, movieID)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_moviesListFragment_to_movieDetailedInfoFragment, bundle)
    }


}