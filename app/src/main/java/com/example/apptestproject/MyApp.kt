package com.example.apptestproject

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.example.apptestproject.di.DaggerLocationComponent
import com.example.apptestproject.di.LocationComponent
import com.example.apptestproject.di.LocationModule

class MyApp : Application() {
    lateinit var locationComponent: LocationComponent
        private set

    fun initializeLocationComponent(fragmentActivity: FragmentActivity) {
        locationComponent = DaggerLocationComponent.builder()
            .locationModule(LocationModule(fragmentActivity))
            .build()
    }
}

