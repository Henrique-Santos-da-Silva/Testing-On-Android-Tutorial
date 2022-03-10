package com.youtube.tutorials.testingonandroidtutorial.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.youtube.tutorials.testingonandroidtutorial.databinding.ItemImageBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor(private val glide: RequestManager) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            with(binding) {
                glide.load(url).into(ivShoppingImage)

                root.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(url)
                    }
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }

    private val differ: AsyncListDiffer<String> = AsyncListDiffer(this, diffCallback)

    var images: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: ItemImageBinding = ItemImageBinding.inflate(layoutInflater, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url: String = images[position]
        holder.bind(url)
    }

    override fun getItemCount(): Int = images.size
}