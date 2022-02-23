package com.youtube.tutorials.testingonandroidtutorial.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingItem
import com.youtube.tutorials.testingonandroidtutorial.data.remote.responses.ImageResponse
import com.youtube.tutorials.testingonandroidtutorial.other.Resource

class FakeShoppingRepository: ShoppingRepository {

    private val shoppingItems: MutableList<ShoppingItem> = mutableListOf()

    private val observableShoppingItem = MutableLiveData<List<ShoppingItem>>()
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableShoppingItem.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> = observableShoppingItem

    override fun observeTotalPrice(): LiveData<Float> = observableTotalPrice

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(emptyList(), 0, 0,))
        }
    }
}