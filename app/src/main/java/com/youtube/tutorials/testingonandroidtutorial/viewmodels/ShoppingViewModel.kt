package com.youtube.tutorials.testingonandroidtutorial.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingItem
import com.youtube.tutorials.testingonandroidtutorial.data.remote.responses.ImageResponse
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

    }

    fun searchForImage(imageQuery: String) {

    }


}