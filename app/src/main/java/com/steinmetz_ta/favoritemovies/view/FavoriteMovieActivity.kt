package com.steinmetz_ta.favoritemovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.steinmetz_ta.favoritemovies.R
import com.steinmetz_ta.favoritemovies.databinding.ActivityFavoriteMovieBinding

class FavoriteMovieActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityFavoriteMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(bindings.root)
    }
}