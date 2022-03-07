package com.youtube.tutorials.testingonandroidtutorial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.youtube.tutorials.testingonandroidtutorial.databinding.FragmentAddShoppingItemBinding
import com.youtube.tutorials.testingonandroidtutorial.viewmodels.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@AndroidEntryPoint
@FragmentScoped
class AddShoppingItemFragment : Fragment() {
    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding: FragmentAddShoppingItemBinding? get() = _binding

    private val viewModel: ShoppingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =  FragmentAddShoppingItemBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.ivShoppingImage?.setOnClickListener {
            findNavController().navigate(AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment())
        }

        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurlImage("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}