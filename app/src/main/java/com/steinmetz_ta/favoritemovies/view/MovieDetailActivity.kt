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

        /**
         * Starts search for the selected movie in the database
         * */
        movieViewModel.searchMovie(movie.id)

        /**
         * Collects the result of the movie search from above.
         * */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.movie.collect {
                    /**
                     * if there is already an db entry with that movie the option to
                     * delte it from the db is shown. If it's not, then by default is
                     * the add to favorite button shown
                     * */
                    if(it != null) {
                        bindings.ibMovieDetailAddMovieToFav.visibility = View.GONE
                        bindings.ibMovieDetailDeleteMovieFromFav.visibility = View.VISIBLE
                    }
                }
            }
        }

        /**
         * Initializing the UI with the movie data
         * */
        bindings.tvMovieDetailMovieTitle.text = movie.title
        bindings.tvMovieDetailMovieDesc.text = movie.description
        Glide.with(this).asBitmap().load(movie.image).into(bindings.ivMovieDetailMovieLogo)

        /**
         * onClickListener for adding and removing a movie from the favorites
         * */
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