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
    fun inject(fragment: HomeFragment)
    fun inject(fragment: DishListFragment)
    fun inject(fragment: CartFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
