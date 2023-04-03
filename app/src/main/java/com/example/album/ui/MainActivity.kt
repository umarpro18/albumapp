package com.example.album.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.album.R
import com.example.album.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple class.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController: NavController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

    }
}