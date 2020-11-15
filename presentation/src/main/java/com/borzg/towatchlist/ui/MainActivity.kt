package com.borzg.towatchlist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.borzg.towatchlist.R
import com.borzg.towatchlist.databinding.ActivityMainBinding
import com.borzg.towatchlist.utils.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationBar()
    }

    private var currentNavController: LiveData<NavController>? = null

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        // Setup the bottom navigation view with a list of navigation graphs
        val liveData = binding.bottomNavView.setupWithNavController(
            navGraphIds = listOf(
                R.navigation.watchlist_navigation,
                R.navigation.search_navigation
            ),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        liveData.observe(this) { ctrl ->
            if (actionBar != null) setupActionBarWithNavController(ctrl)
        }
        currentNavController = liveData
    }

}
