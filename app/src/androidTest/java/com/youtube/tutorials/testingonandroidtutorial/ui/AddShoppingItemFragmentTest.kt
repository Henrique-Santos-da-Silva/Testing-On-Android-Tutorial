package com.youtube.tutorials.testingonandroidtutorial.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.youtube.tutorials.testingonandroidtutorial.MainAndroidTestCoroutineRule
import com.youtube.tutorials.testingonandroidtutorial.R
import com.youtube.tutorials.testingonandroidtutorial.di.AppModule
import com.youtube.tutorials.testingonandroidtutorial.getOrAwaitValue
import com.youtube.tutorials.testingonandroidtutorial.launchFragmentInHiltContainer
import com.youtube.tutorials.testingonandroidtutorial.repositories.FakeShoppingAndroidTestRepository
import com.youtube.tutorials.testingonandroidtutorial.viewmodels.ShoppingViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@UninstallModules(AppModule::class)
@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainAndroidTestCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel


    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = ShoppingViewModel(FakeShoppingAndroidTestRepository())
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController: NavController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()

    }

    @Test
    fun clickShoppingImage_navigatesToImagePickFragment() {
        val navController: NavController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.iv_shopping_image)).perform(click())

        verify(navController).navigate(AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment())
    }

    @Test
    fun pressBackButton_emptiesImageUrl() {
        val navController: NavController = mock(NavController::class.java)
        var viewModel1: ShoppingViewModel? = null

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewModel1 = viewModel
        }
        pressBack()

        viewModel.setCurlImage("")
        val url: String? = viewModel1?.curlImageUrl?.getOrAwaitValue()
        assertThat(url).isEmpty()
    }

}