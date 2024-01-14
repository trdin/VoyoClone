package com.example.voyoclone.adapters


import FrontPayload

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.voyoclone.R
import com.example.voyoclone.databinding.ActivityMainBinding
import com.example.voyoclone.databinding.CategoryItemBinding

import com.squareup.picasso.Picasso
import timber.log.Timber

class CategoryAdapter(var movieList: List<FrontPayload>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var _binding: CategoryItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(_binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movieList[position]
        var image = ""

        if (item.portraitImage != null) {
            image = item.portraitImage.src ?: ""

        } else if (item.image != null) {
            image = item.image.src ?: ""
        }

        if (image.isNotBlank()) {
            //replacing the placeholder in the URL
            image = image.replace("PLACEHOLDER", "298x441")
            Picasso.get().load(image).error(R.drawable.default_category_image).into(holder.movieImg)
        }else{
            Timber.tag("ImageLoading").e("Empty image URL for position $position")
            holder.movieImg.setImageResource(R.drawable.default_category_image)

        }
    }
    override fun getItemCount(): Int {
        return movieList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var movieImg: ImageView = ItemView.findViewById(R.id.category_item_image)
    }

}