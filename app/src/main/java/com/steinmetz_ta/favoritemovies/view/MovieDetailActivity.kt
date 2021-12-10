package com.steinmetz_ta.favoritemovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.steinmetz_ta.favoritemovies.R
import com.steinmetz_ta.favoritemovies.databinding.ActivityMovieDetailBinding
import com.steinmetz_ta.favoritemovies.databinding.ActivitySearchMovieBinding
import com.steinmetz_ta.favoritemovies.model.Result
import com.steinmetz_ta.favoritemovies.util.Constants

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        var movie: Result = intent.getSerializableExtra(Constants.MOVIE_KEY) as Result

        bindings.tvMovieDetailMovieTitle.text = movie.title
        bindings.tvMovieDetailMovieDesc.text = movie.description
        Glide.with(this).asBitmap().load(movie.image).into(bindings.ivMovieDetailMovieLogo)


    }
}