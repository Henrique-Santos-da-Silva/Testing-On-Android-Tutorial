package com.youtube.tutorials.testingonandroidtutorial.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.youtube.tutorials.testingonandroidtutorial.di.AppModule
import com.youtube.tutorials.testingonandroidtutorial.getOrAwaitValue
import com.youtube.tutorials.testingonandroidtutorial.launchFragmentInHiltContainer
import com.youtube.tutorials.testingonandroidtutorial.ui.ShoppingFragment
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(AppModule::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao



    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ShoppingFragment> {  }
    }

    @Test
    fun insertShoppingItem(): TestResult = runTest {
        val shoppingItem = ShoppingItem(1, "name", 1, 1f, "url")
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItems: List<ShoppingItem> = dao.getAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() : TestResult= runTest {
        val shoppingItem = ShoppingItem(1, "name", 1, 1f, "url")
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems: List<ShoppingItem> = dao.getAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun totalPriceSum(): TestResult = runTest {
        val shoppingItem1 = ShoppingItem(1, "name", 2, 10f, "url")
        val shoppingItem2 = ShoppingItem(2, "name", 4, 5.5f, "url")
        val shoppingItem3 = ShoppingItem(3, "name", 0, 100f, "url")
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum: Float = dao.getTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
    }
}