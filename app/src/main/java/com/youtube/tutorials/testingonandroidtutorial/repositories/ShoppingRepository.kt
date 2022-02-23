package com.youtube.tutorials.testingonandroidtutorial.repositories

import androidx.lifecycle.LiveData
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingItem
import com.youtube.tutorials.testingonandroidtutorial.data.remote.responses.ImageResponse
import com.youtube.tutorials.testingonandroidtutorial.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}