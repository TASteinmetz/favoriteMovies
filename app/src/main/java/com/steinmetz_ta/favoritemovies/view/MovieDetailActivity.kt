package com.steinmetz_ta.favoritemovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.steinmetz_ta.favoritemovies.R
import com.steinmetz_ta.favoritemovies.databinding.ActivityMovieDetailBinding
import com.steinmetz_ta.favoritemovies.databinding.ActivitySearchMovieBinding
import com.steinmetz_ta.favoritemovies.model.Result
import com.steinmetz_ta.favoritemovies.util.Constants
import com.steinmetz_ta.favoritemovies.viewmodel.MovieViewModel
import com.steinmetz_ta.favoritemovies.viewmodel.MovieViewModelFactory

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMovieDetailBinding

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        var movie: Result = intent.getSerializableExtra(Constants.MOVIE_KEY) as Result

        bindings.tvMovieDetailMovieTitle.text = movie.title
        bindings.tvMovieDetailMovieDesc.text = movie.description
        Glide.with(this).asBitmap().load(movie.image).into(bindings.ivMovieDetailMovieLogo)


        bindings.ibMovieDetailAddMovieToFav.setOnClickListener {
            movieViewModel.insertMovie(movie)
            Log.d("FavoriteMovies", "${movieViewModel.allMovies}")
        }
    }
}