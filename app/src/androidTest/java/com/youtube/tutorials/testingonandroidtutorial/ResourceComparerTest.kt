package com.youtube.tutorials.testingonandroidtutorial


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ResourceComparerTest {
    private lateinit var resourceComparer: ResourceComparer

    @Before
    fun setup() {
        resourceComparer = ResourceComparer()
    }

    // Se string do resource for igual a string digitada, retorne true
    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val result: Boolean = resourceComparer.isEqual(context, R.string.app_name, "Testing On Android Tutorial")
        assertThat(result).isTrue()
    }
    @Test
    fun stringResourceDifferentAsGivenString_returnsFalse() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val result: Boolean = resourceComparer.isEqual(context, R.string.app_name, "Hello")
        assertThat(result).isFalse()
    }
}