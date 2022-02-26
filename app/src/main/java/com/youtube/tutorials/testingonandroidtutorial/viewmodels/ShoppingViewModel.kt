package com.youtube.tutorials.testingonandroidtutorial.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingItem
import com.youtube.tutorials.testingonandroidtutorial.data.remote.responses.ImageResponse
import com.youtube.tutorials.testingonandroidtutorial.other.Constants
import com.youtube.tutorials.testingonandroidtutorial.other.Event
import com.youtube.tutorials.testingonandroidtutorial.other.Resource
import com.youtube.tutorials.testingonandroidtutorial.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(private val repository: ShoppingRepository): ViewModel()  {

    val shoppingItems: LiveData<List<ShoppingItem>> = repository.observeAllShoppingItems()

    val totalPrice: LiveData<Float> = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curlImageUrl = MutableLiveData<String>()
    val curlImageUrl: LiveData<String> = _curlImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurlImage(url: String) {
        _curlImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem): Job = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem): Job = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun validationInsertShoppingItem(name: String, amountString: String, priceString: String) {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }

        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The name of the of item must not exceed ${Constants.MAX_NAME_LENGTH} character", null)))
            return
        }

        if (priceString.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The price of the of item must not exceed ${Constants.MAX_PRICE_LENGTH} character", null)))
            return
        }

        val amount: Int = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter a valid amount", null)))
            return
        }

        val shoppingItem = ShoppingItem(name = name, amount = amount, price = priceString.toFloat(), imageUrl = _curlImageUrl.value ?: "")
        insertShoppingItemIntoDb(shoppingItem)
        setCurlImage("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty()) return

        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response: Resource<ImageResponse> = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }


}