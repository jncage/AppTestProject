package com.example.apptestproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.apptestproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.graph.findNode(navController.graph.startDestinationId)?.id.let { startDestinationId ->
            val builder = startDestinationId?.let {
                NavOptions.Builder()
                    .setPopUpTo(it, false)
                    .setLaunchSingleTop(true)
                    .build()
            }
            if (startDestinationId != null) {
                navController.navigate(startDestinationId, null, builder)
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val builder = NavOptions.Builder().setLaunchSingleTop(true)
            val destination = navController.graph.findNode(menuItem.itemId)
            navController.graph.findStartDestination().id.let {
                builder.setPopUpTo(it, inclusive = false)
            }
            val options = builder.build()
            destination?.id?.let {
                navController.navigate(it, null, options)
            }
            true
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.label == getString(R.string.home)) {
                bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
            }
        }
    }
}

