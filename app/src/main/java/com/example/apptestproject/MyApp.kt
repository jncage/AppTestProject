package com.example.apptestproject

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.example.apptestproject.di.DaggerLocationComponent
import com.example.apptestproject.di.LocationComponent

class MyApp : Application() {
    lateinit var locationComponent: LocationComponent
        private set

    private var fragmentActivity: FragmentActivity? = null

    fun setFragmentActivity(activity: FragmentActivity) {
        fragmentActivity = activity
        initializeLocationComponent()
    }

    private fun initializeLocationComponent() {
        fragmentActivity?.let{
            locationComponent = DaggerLocationComponent.builder()
                .application(this)
                .fragmentActivity(it)
                .build()
        }
    }
}
