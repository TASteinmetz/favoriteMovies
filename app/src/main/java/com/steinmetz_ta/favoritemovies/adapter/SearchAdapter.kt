package com.steinmetz_ta.favoritemovies.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.steinmetz_ta.favoritemovies.model.Result
import androidx.recyclerview.widget.RecyclerView
import com.steinmetz_ta.favoritemovies.databinding.AdapterSearchmovieBinding
import com.steinmetz_ta.favoritemovies.model.MovieResponse

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var movies = mutableListOf<Result>()

    fun setMovieList(movies: List<Result>) {
        if(movies.isEmpty()){
            Log.e("Movies", "List is empty")
        } else {
            this.movies = movies.toMutableList()
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterSearchmovieBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movieTitle.text = movie.title
        holder.binding.movieDescription.text = movie.description
        holder.binding.movieId.text = movie.id
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class SearchViewHolder(val binding: AdapterSearchmovieBinding)
        : RecyclerView.ViewHolder(binding.root) {

    }

}