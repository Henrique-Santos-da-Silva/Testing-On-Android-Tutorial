package com.youtube.tutorials.testingonandroidtutorial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.youtube.tutorials.testingonandroidtutorial.adapters.ImageAdapter
import com.youtube.tutorials.testingonandroidtutorial.databinding.FragmentImagePickBinding
import com.youtube.tutorials.testingonandroidtutorial.viewmodels.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@AndroidEntryPoint
@FragmentScoped
class ImagePickFragment @Inject constructor(val imageAdapter: ImageAdapter) : Fragment() {
    private var _binding: FragmentImagePickBinding? = null
    private val binding: FragmentImagePickBinding? get() = _binding

     val viewModel: ShoppingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
       _binding = FragmentImagePickBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurlImage(it)
        }
    }

    private fun setupRecyclerView() {
        binding?.rvImages?.let {
            it.adapter = imageAdapter
            it.layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }
}