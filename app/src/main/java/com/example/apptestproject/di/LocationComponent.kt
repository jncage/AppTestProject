package com.example.apptestproject.di

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.example.apptestproject.MyApp
import com.example.apptestproject.ui.MainActivity
import com.example.apptestproject.utils.LocationHelper
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
@Singleton
@Component(modules = [LocationModule::class])
interface LocationComponent {
    fun inject(activity: MainActivity)
    @Component.Builder
    interface Builder {
        fun build(): LocationComponent

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun fragmentActivity(fragmentActivity: FragmentActivity): Builder
    }
}
