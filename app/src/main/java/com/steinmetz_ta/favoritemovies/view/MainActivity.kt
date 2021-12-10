/**
 * In this Activtiy is the main menu. Here you can choose to search for
 * movies you want to add to your favorites or you can open your favorite list
 *
 * @author: tobi.steinmetz@gmail.com
 * */
package com.steinmetz_ta.favoritemovies.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.steinmetz_ta.favoritemovies.R
import com.steinmetz_ta.favoritemovies.data.MovieApi
import com.steinmetz_ta.favoritemovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)


        bindings.btnMainSearch.setOnClickListener {
            intent = Intent(this, SearchMovieActivity::class.java)
            startActivity(intent)
        }

        //https://imdb-api.com/en/API/SearchTitle/k_wqjoq51m/inception
    }
}