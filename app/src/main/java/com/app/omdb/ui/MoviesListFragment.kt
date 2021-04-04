package com.app.omdb.ui

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.omdb.R
import com.app.omdb.databinding.FragmentMoviesListBinding
import com.app.omdb.databinding.LayoutSearchBinding
import com.app.omdb.network.OMDBApi
import com.app.omdb.network.Resource
import com.app.omdb.repository.MovieRepository
import com.app.omdb.responses.Search
import com.app.omdb.ui.adapter.MovieListAdapter
import com.app.omdb.ui.base.BaseFragment
import com.app.omdb.ui.common.Constants.Companion.API_KEY
import com.app.omdb.ui.common.Constants.Companion.BUNDLE_KEY_MOVIE_ID
import com.app.utils.ProgressDialog

class MoviesListFragment :
    BaseFragment<MoviesViewModel, FragmentMoviesListBinding, MovieRepository>(),
    FragmentsCommunicator {

    private lateinit var moviesListAdapter: MovieListAdapter
    private lateinit var dialog: Dialog
    private var moviesList: ArrayList<Search> = arrayListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog = ProgressDialog.progressDialog(context)
        initializeObserver()
        setupUI()
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

    private fun initializeObserver() {
        viewModel.moviesListResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    moviesList = it.value.Search as ArrayList<Search>
                    moviesListAdapter.setNewData(moviesList)
                }
                is Resource.Failure -> {
                    hideLoading()
                }
            }
        })
    }

    private fun setupUI() {
        moviesListAdapter = MovieListAdapter(moviesList, context, this)

        binding.moviesListRc.layoutManager = LinearLayoutManager(context)
        binding.moviesListRc.adapter = moviesListAdapter
        binding.moviesListRc.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        if (viewModel.moviesListResponse.value == null) {
            displayLoading()
            viewModel.fetchMoviesList(API_KEY, "Frozen")
        }
        binding.searchIcon.setOnClickListener {
            openSearchDialog()
        }
    }

    private fun displayLoading() {
        dialog.show()
    }

    private fun hideLoading() {
        dialog.hide()
    }

    private fun openSearchDialog() {

        context?.let {
            val searchBinding = LayoutSearchBinding.inflate(LayoutInflater.from(it))
            val dialog = Dialog(it)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(searchBinding.root)
            searchBinding.searchBtn.setOnClickListener {
                if (TextUtils.isEmpty(searchBinding.searchTv.text)) {
                    Toast.makeText(
                        context,
                        getString(R.string.text_search_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    dialog.dismiss()
                    displayLoading()
                    viewModel.fetchMoviesList(API_KEY, searchBinding.searchTv.text.toString())
                }

            }
            dialog.show()
        }

    }


}