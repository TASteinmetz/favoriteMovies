package com.steinmetz_ta.favoritemovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.steinmetz_ta.favoritemovies.databinding.ActivityMovieDetailBinding
import com.steinmetz_ta.favoritemovies.model.Result
import com.steinmetz_ta.favoritemovies.util.Constants
import com.steinmetz_ta.favoritemovies.viewmodel.MovieViewModel
import com.steinmetz_ta.favoritemovies.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMovieDetailBinding

    private var movieResult: Result? = null

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        var movie: Result = intent.getSerializableExtra(Constants.MOVIE_KEY) as Result


        movieViewModel.searchMovie(movie.id)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.movie.collect {
                    if(it != null) {
                        bindings.ibMovieDetailAddMovieToFav.visibility = View.GONE
                        bindings.ibMovieDetailDeleteMovieFromFav.visibility = View.VISIBLE
                    }
                }
            }
        }


        bindings.tvMovieDetailMovieTitle.text = movie.title
        bindings.tvMovieDetailMovieDesc.text = movie.description
        Glide.with(this).asBitmap().load(movie.image).into(bindings.ivMovieDetailMovieLogo)


        bindings.ibMovieDetailAddMovieToFav.setOnClickListener {
            movieViewModel.insertMovie(movie)
            Log.d("FavoriteMovies", "${movieViewModel.allMovies}")
            bindings.ibMovieDetailAddMovieToFav.visibility = View.GONE
            bindings.ibMovieDetailDeleteMovieFromFav.visibility = View.VISIBLE
        }

        bindings.ibMovieDetailDeleteMovieFromFav.setOnClickListener {
            movieViewModel.deleteMovie(movie.id)
            bindings.ibMovieDetailAddMovieToFav.visibility = View.VISIBLE
            bindings.ibMovieDetailDeleteMovieFromFav.visibility = View.GONE
        }
    }
}