package com.youtube.tutorials.testingonandroidtutorial.repositories

import androidx.lifecycle.LiveData
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingDao
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingItem
import com.youtube.tutorials.testingonandroidtutorial.data.remote.PixabayAPI
import com.youtube.tutorials.testingonandroidtutorial.data.remote.responses.ImageResponse
import com.youtube.tutorials.testingonandroidtutorial.other.Resource
import retrofit2.Response
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(private val shoppingDao: ShoppingDao, private val pixabayAPI: PixabayAPI): ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.getAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> = shoppingDao.getTotalPrice()

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response: Response<ImageResponse> = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let { imageResponse ->
                    return@let Resource.success(imageResponse)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}