package com.example.apptestproject.di

import android.content.Context
import com.example.apptestproject.ui.CartActivity
import com.example.apptestproject.ui.DishListActivity
import com.example.apptestproject.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [LocationModule::class, ApiModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: DishListActivity)
    fun inject(activity: CartActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
