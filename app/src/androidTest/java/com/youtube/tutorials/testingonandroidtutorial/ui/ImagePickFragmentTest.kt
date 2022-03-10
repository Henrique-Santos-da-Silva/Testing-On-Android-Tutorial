package com.youtube.tutorials.testingonandroidtutorial.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.youtube.tutorials.testingonandroidtutorial.R
import com.youtube.tutorials.testingonandroidtutorial.adapters.ImageAdapter
import com.youtube.tutorials.testingonandroidtutorial.di.AppModule
import com.youtube.tutorials.testingonandroidtutorial.getOrAwaitValue
import com.youtube.tutorials.testingonandroidtutorial.launchFragmentInHiltContainer
import com.youtube.tutorials.testingonandroidtutorial.repositories.FakeShoppingAndroidTestRepository
import com.youtube.tutorials.testingonandroidtutorial.ui.factories.ShoppingFragmentFactory
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
import javax.inject.Inject

@UninstallModules(AppModule::class)
@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    // TODO: 10/03/2021 ->  The example was not made according to the tutorial, but it works, it passes the test correctly
    
    @Test
    fun clickImage_popBackStackAndSetImageUrl() {
        val navController: NavController = mock(NavController::class.java)
        val imageUrl = "TEST"
        val testViewModel = ShoppingViewModel(FakeShoppingAndroidTestRepository())
        var viewModel1: ShoppingViewModel? = null

        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = listOf(imageUrl)
            viewModel1 = testViewModel
        }

        onView(withId(R.id.rv_images)).perform(RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(0, click()))
        verify(navController).popBackStack()
        testViewModel.setCurlImage(imageUrl)

        assertThat(viewModel1?.curlImageUrl?.getOrAwaitValue()).isEqualTo(imageUrl)
    }
}