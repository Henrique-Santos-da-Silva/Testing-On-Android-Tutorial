package com.youtube.tutorials.testingonandroidtutorial.di

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.youtube.tutorials.testingonandroidtutorial.R
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingDao
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingItemDatabase
import com.youtube.tutorials.testingonandroidtutorial.data.remote.PixabayAPI
import com.youtube.tutorials.testingonandroidtutorial.other.Constants
import com.youtube.tutorials.testingonandroidtutorial.repositories.DefaultShoppingRepository
import com.youtube.tutorials.testingonandroidtutorial.repositories.ShoppingRepository
import com.youtube.tutorials.testingonandroidtutorial.ui.factories.ShoppingFragmentFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TestAppModule {

    @Binds
    abstract fun providerDefaultShoppingRepository(defaultShoppingRepository: DefaultShoppingRepository): ShoppingRepository

    @Binds
    abstract fun providerFragmentFactory(fragmentFactory: ShoppingFragmentFactory): FragmentFactory

    companion object {
        @Singleton
        @Provides
        fun provideGlideInstance(@ApplicationContext context: Context): RequestManager =
            Glide.with(context).setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_image).error(R.drawable.ic_image))


        @Singleton
        @Provides
        fun providerShoppingItemDatabase(@ApplicationContext context: Context) =
            Room.inMemoryDatabaseBuilder(context, ShoppingItemDatabase::class.java)
                .allowMainThreadQueries().build()

        @Singleton
        @Provides
        fun providerShoppingDao(database: ShoppingItemDatabase): ShoppingDao = database.shoppingDao()

        @Singleton
        @Provides
        fun providerPixabayApi(): PixabayAPI {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(PixabayAPI::class.java)
        }
    }

}