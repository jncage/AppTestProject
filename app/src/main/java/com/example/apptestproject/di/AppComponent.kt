package com.example.apptestproject.di

import android.content.Context
import com.example.apptestproject.ui.CartFragment
import com.example.apptestproject.ui.DishListFragment
import com.example.apptestproject.ui.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [LocationModule::class, ApiModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: HomeFragment)
    fun inject(activity: DishListFragment)
    fun inject(activity: CartFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
