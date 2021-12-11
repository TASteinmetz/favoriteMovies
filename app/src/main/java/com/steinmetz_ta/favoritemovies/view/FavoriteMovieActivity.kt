package com.steinmetz_ta.favoritemovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.steinmetz_ta.favoritemovies.R
import com.steinmetz_ta.favoritemovies.adapter.SearchAdapter
import com.steinmetz_ta.favoritemovies.databinding.ActivityFavoriteMovieBinding
import com.steinmetz_ta.favoritemovies.viewmodel.MovieViewModel
import com.steinmetz_ta.favoritemovies.viewmodel.MovieViewModelFactory

class FavoriteMovieActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityFavoriteMovieBinding

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository)
    }

    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        adapter = SearchAdapter(this)

        bindings.rvFavoriteMovies.layoutManager = LinearLayoutManager(
                this,
        LinearLayoutManager.VERTICAL,
        false
        )
        bindings.rvFavoriteMovies.adapter = adapter
        bindings.rvFavoriteMovies.visibility = View.VISIBLE

        movieViewModel.allMovies.observe(this, {
            adapter.setMovieList(it)
        })



    }
}