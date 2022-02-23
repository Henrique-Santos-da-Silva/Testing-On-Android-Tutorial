package com.youtube.tutorials.testingonandroidtutorial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.youtube.tutorials.testingonandroidtutorial.databinding.FragmentImagePickBinding
import com.youtube.tutorials.testingonandroidtutorial.viewmodels.ShoppingViewModel

class ImagePickFragment : Fragment() {
    private var _binding: FragmentImagePickBinding? = null
    private val binding: FragmentImagePickBinding? get() = _binding

    private val viewModel: ShoppingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
       _binding = FragmentImagePickBinding.inflate(inflater, container, false)
        return binding?.root
    }
}