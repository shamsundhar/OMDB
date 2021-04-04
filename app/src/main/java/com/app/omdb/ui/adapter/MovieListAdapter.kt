package com.app.excelli

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.omdb.R
import com.app.omdb.databinding.MovieListItemBinding
import com.app.omdb.responses.Search
import com.app.omdb.ui.FragmentsCommunicator
import com.app.omdb.ui.MainActivity
import com.app.omdb.ui.MovieDetailedInfoFragment
import com.squareup.picasso.Picasso


class MovieListAdapter(private var moviesList: ArrayList<Search>, private var context: Context?,private val fragmentsCommunicator: FragmentsCommunicator) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    class MovieListViewHolder(val binding : MovieListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return  MovieListViewHolder(MovieListItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val currentItem = moviesList[position]
        holder.binding.mvTitle.text = currentItem.Title
        holder.binding.mvDesc.text = currentItem.Type
        Picasso.with(context).load(currentItem.Poster).into(holder.binding.imageView)
        holder.binding.movieItemParent.setOnClickListener(View.OnClickListener {
            fragmentsCommunicator.onRecyclerViewClicked(currentItem.imdbID)
        })
    }

    private fun openMovieDeatiledInfoActivity(imdbID: String) {

    }


    fun setNewData(topic: ArrayList<Search>?) {
        if (topic != null) {
            moviesList = topic
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        println("getItemCount : "+moviesList.size)
        return moviesList.size
    }


}
