package com.example.apptestproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
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
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val destination = navController.graph.findNode(menuItem.itemId)
            destination?.id?.let {
                navController.navigate(
                    it, null, NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(it, false)
                        .build()
                )
            }
            true
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.dishListFragment) {
                bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
            }
        }
    }
}

