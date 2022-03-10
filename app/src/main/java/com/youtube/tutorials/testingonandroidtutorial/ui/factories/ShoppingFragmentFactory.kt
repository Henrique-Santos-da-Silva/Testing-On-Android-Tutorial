package com.youtube.tutorials.testingonandroidtutorial.ui.factories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.youtube.tutorials.testingonandroidtutorial.adapters.ImageAdapter
import com.youtube.tutorials.testingonandroidtutorial.ui.ImagePickFragment
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(private val imageAdapter: ImageAdapter): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}