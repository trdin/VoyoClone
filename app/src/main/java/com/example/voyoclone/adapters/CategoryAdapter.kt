package com.example.voyoclone.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.voyoclone.R
import com.example.voyoclone.databinding.CategoryItemBinding
import com.example.voyoclone.models.FrontPayload

import com.squareup.picasso.Picasso
import timber.log.Timber

class CategoryAdapter(var itemList: List<FrontPayload>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var _binding: CategoryItemBinding
    private var listener: CategoryAdapter.OnClickListener? = null


    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(_binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = itemList[position]
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
            holder.movieCard.setOnClickListener { p0 ->
                listener?.onClick(
                    p0,
                    position
                )
            }
        } else {
            Timber.tag("ImageLoading").e("Empty image URL for position $position")
            holder.movieImg.setImageResource(R.drawable.default_category_image)

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var movieImg: ImageView = ItemView.findViewById(R.id.category_item_image)
        var movieCard: CardView = ItemView.findViewById(R.id.movie_card)
    }


    interface OnClickListener {
        fun onClick(p0: View?, position: Int)
    }

}