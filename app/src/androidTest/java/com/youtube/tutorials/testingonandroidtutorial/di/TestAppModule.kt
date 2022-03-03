package com.youtube.tutorials.testingonandroidtutorial.di

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingDao
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingItemDatabase
import com.youtube.tutorials.testingonandroidtutorial.data.remote.PixabayAPI
import com.youtube.tutorials.testingonandroidtutorial.other.Constants
import com.youtube.tutorials.testingonandroidtutorial.repositories.FakeShoppingAndroidTestRepository
import com.youtube.tutorials.testingonandroidtutorial.repositories.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
//@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
object TestAppModule {

    @Provides
    fun provideInMemoryDb() = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ShoppingItemDatabase::class.java)
        .allowMainThreadQueries().build()

    @Provides
    fun providerShoppingDaoTest(database: ShoppingItemDatabase): ShoppingDao = database.shoppingDao()

    @Provides
    fun providerPixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestTestModule {
        @Binds
        abstract fun providerDefaultShoppingRepository(defaultShoppingRepository: FakeShoppingAndroidTestRepository): ShoppingRepository
    }
}