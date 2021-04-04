package com.app.omdb.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.omdb.databinding.MovieListItemBinding
import com.app.omdb.responses.Search
import com.app.omdb.ui.FragmentsCommunicator
import com.squareup.picasso.Picasso


class MovieListAdapter(
    private var moviesList: ArrayList<Search>,
    private var context: Context?,
    private val fragmentsCommunicator: FragmentsCommunicator
) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    class MovieListViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val currentItem = moviesList[position]
        holder.binding.mvTitle.text = currentItem.Title
        holder.binding.mvDesc.text = currentItem.Year
        Picasso.with(context).load(currentItem.Poster).into(holder.binding.imageView)
        holder.binding.movieItemParent.setOnClickListener {
            fragmentsCommunicator.onRecyclerViewClicked(currentItem.imdbID)
        }
    }


    fun setNewData(list: ArrayList<Search>?) {
        if (list != null) {
            moviesList = list
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


}
