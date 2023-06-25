package com.example.apptestproject

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.example.apptestproject.di.AppComponent
import com.example.apptestproject.di.DaggerAppComponent
import com.example.apptestproject.di.LocationModule

class MyApp : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}
