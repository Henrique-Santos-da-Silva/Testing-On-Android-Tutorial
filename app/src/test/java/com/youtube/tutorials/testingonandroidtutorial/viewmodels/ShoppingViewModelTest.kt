package com.youtube.tutorials.testingonandroidtutorial.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.youtube.tutorials.testingonandroidtutorial.MainCoroutineRule
import com.youtube.tutorials.testingonandroidtutorial.data.local.ShoppingItem
import com.youtube.tutorials.testingonandroidtutorial.getOrAwaitValueTest
import com.youtube.tutorials.testingonandroidtutorial.other.Constants
import com.youtube.tutorials.testingonandroidtutorial.other.Event
import com.youtube.tutorials.testingonandroidtutorial.other.Resource
import com.youtube.tutorials.testingonandroidtutorial.other.Status
import com.youtube.tutorials.testingonandroidtutorial.repositories.FakeShoppingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel


    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field, returns error`() {
        viewModel.validationInsertShoppingItem("name", "", "3.0")

        val value: Event<Resource<ShoppingItem>> = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, returns error`() {
        val stringName: String = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.validationInsertShoppingItem(stringName, "5", "3.0")

        val value: Event<Resource<ShoppingItem>> = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, returns error`() {
        val stringPrice: String = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.validationInsertShoppingItem("name", "5", stringPrice)

        val value: Event<Resource<ShoppingItem>> = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`() {

        viewModel.validationInsertShoppingItem("name", "9999999999999999999999999", "3.0")

        val value: Event<Resource<ShoppingItem>> = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, returns true`() {

        viewModel.validationInsertShoppingItem("name", "5", "3.0")

        val value: Event<Resource<ShoppingItem>> = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `if set curl image parameter string is filled, return false`() {
        viewModel.setCurlImage("Image url")
        val value: String = viewModel.curlImageUrl.getOrAwaitValueTest()

        assertThat(value).isNotEmpty()
    }

    @Test
    fun `if set curl image parameter string is empty, return true`() {
        viewModel.setCurlImage("")
        val value: String = viewModel.curlImageUrl.getOrAwaitValueTest()

        assertThat(value).isEmpty()
    }


}