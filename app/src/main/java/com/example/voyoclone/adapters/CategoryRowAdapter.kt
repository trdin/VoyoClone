package com.example.voyoclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voyoclone.databinding.CategoryRowBinding
import com.example.voyoclone.databinding.TopSliderBinding
import com.example.voyoclone.models.FrontData

class CategoryRowAdapter(private val context: Context, private val categories: MutableList<FrontData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_SLIDER = 0
    private val VIEW_TYPE_CATEGORY_ROW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SLIDER -> {
                val binding = TopSliderBinding.inflate(inflater, parent, false)
                SliderViewHolder(binding)
            }
            VIEW_TYPE_CATEGORY_ROW -> {
                val binding = CategoryRowBinding.inflate(inflater, parent, false)
                CategoryRowViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_SLIDER -> {
                val sliderViewHolder = holder as SliderViewHolder
               categories[position]
                sliderViewHolder.bind(categories[position])
            }
            VIEW_TYPE_CATEGORY_ROW -> {
                val categoryRowViewHolder = holder as CategoryRowViewHolder
                val category = categories[position]
                categoryRowViewHolder.bind(category)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (categories[position].dataId == "splash") {
            VIEW_TYPE_SLIDER
        } else {
            VIEW_TYPE_CATEGORY_ROW
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }


    inner class SliderViewHolder(private val binding: TopSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(slides: FrontData) {

            val sliderPagerAdapter = SliderPagerAdapter(context, slides.payload)
            binding.sliderPager.adapter = sliderPagerAdapter
        }
    }

    inner class CategoryRowViewHolder(private val binding: CategoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val categoryName: TextView = binding.categoryRowTitle
        val horizontalRecyclerView: RecyclerView = binding.categoryRowRv

        fun bind(category: FrontData) {
            categoryName.text = category.name

            val layoutManager = LinearLayoutManager(
                horizontalRecyclerView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            horizontalRecyclerView.layoutManager = layoutManager

            var elements = category.payload
            //remove the first background image if there is one
            if (elements.isNotEmpty() && elements[0].__typename == "ImageType") {
                elements = elements.drop(1)
            }
            val horizontalAdapter = CategoryAdapter(elements)
            horizontalRecyclerView.adapter = horizontalAdapter
        }

    }
}