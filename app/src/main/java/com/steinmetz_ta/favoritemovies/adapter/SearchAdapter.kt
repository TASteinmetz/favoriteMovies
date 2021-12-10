package com.steinmetz_ta.favoritemovies.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import com.steinmetz_ta.favoritemovies.model.Result
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.steinmetz_ta.favoritemovies.databinding.AdapterSearchmovieBinding
import com.steinmetz_ta.favoritemovies.util.Constants
import com.steinmetz_ta.favoritemovies.view.MovieDetailActivity

class SearchAdapter(context: Context): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var movies = mutableListOf<Result>()
    var context = context

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
        return SearchViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movieTitle.text = movie.title

        Glide.with(context).asBitmap().load(movie.image).into(holder.binding.movieImage)

        holder.binding.movieId.text = movie.description

        holder.binding.btnAddMovie.setOnClickListener {
            var intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(Constants.MOVIE_KEY, movie)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class SearchViewHolder(val binding: AdapterSearchmovieBinding, context: Context)
        : RecyclerView.ViewHolder(binding.root) {

    }

}