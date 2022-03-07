package com.youtube.tutorials.testingonandroidtutorial.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.youtube.tutorials.testingonandroidtutorial.R
import com.youtube.tutorials.testingonandroidtutorial.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
//@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest {

//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment() {
        val navController: NavController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.fab_add_shopping_item)).perform(click())

        verify(navController).navigate(ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment())
    }
}