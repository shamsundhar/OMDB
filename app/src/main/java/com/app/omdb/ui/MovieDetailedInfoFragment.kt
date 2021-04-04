package com.app.omdb.ui


import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.omdb.databinding.FragmentMovieDetailedInfoBinding
import com.app.omdb.network.OMDBApi
import com.app.omdb.network.Resource
import com.app.omdb.repository.MovieRepository
import com.app.omdb.responses.MovieDetailsResponse
import com.app.omdb.ui.base.BaseFragment
import com.app.omdb.ui.common.Constants
import com.app.utils.ImageUtils
import com.app.utils.ProgressDialog
import com.squareup.picasso.Picasso

class MovieDetailedInfoFragment :
    BaseFragment<MoviesViewModel, FragmentMovieDetailedInfoBinding, MovieRepository>() {

    private lateinit var dialog: Dialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog = ProgressDialog.progressDialog(context)
        viewModel.moviesDetailsResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    updateUI(it.value)
                }
                is Resource.Failure -> {
                    hideLoading()
                }
            }
        })

        val movieId: String = arguments?.get(Constants.BUNDLE_KEY_MOVIE_ID) as String
        if (viewModel.moviesDetailsResponse.value == null) {
            displayLoading()
            viewModel.fetchMovieDetailsByID(Constants.API_KEY, movieId)
        }
    }

    private fun displayLoading() {
        dialog.show()
    }

    private fun hideLoading() {
        dialog.hide()
    }

    private fun updateUI(movieDetailsResponse: MovieDetailsResponse) {
        binding.movieTitleTv.text = movieDetailsResponse.Title
        binding.movieRatingTv.text = movieDetailsResponse.Ratings[0].Value
        binding.movieDescriptionTv.text = movieDetailsResponse.Plot
        Picasso.with(context).load(movieDetailsResponse.Poster).into(binding.moviePosterIV)
        updateBackgroundImage(movieDetailsResponse.Poster)
    }

    private fun updateBackgroundImage(url: String) {
        Picasso.with(context).load(url).into(object : com.squareup.picasso.Target {

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val bitmapDrawable =
                    BitmapDrawable(resources, bitmap?.let { ImageUtils.blur(it, context) })
                binding.parentContainer.background = bitmapDrawable
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        })
    }


    override fun getViewModel(): Class<MoviesViewModel> = MoviesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailedInfoBinding =
        FragmentMovieDetailedInfoBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): MovieRepository = MovieRepository(
        remoteDataSource.buildApi(OMDBApi::class.java)
    )


}