package com.example.voyoclone.adapters

import FrontData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voyoclone.R
import com.example.voyoclone.databinding.CategoryItemBinding
import com.example.voyoclone.databinding.CategoryRowBinding

class CategoryRowAdapter(private val categories: MutableList<FrontData>) :
    RecyclerView.Adapter<CategoryRowAdapter.ViewHolder>() {

    private lateinit var _binding: CategoryRowBinding
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category_row_title)
        val horizontalRecyclerView: RecyclerView = itemView.findViewById(R.id.category_row_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = CategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(_binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]

        holder.categoryName.text = category.name

        val layoutManager = LinearLayoutManager(
            holder.horizontalRecyclerView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        holder.horizontalRecyclerView.layoutManager = layoutManager

        var elements = category.payload
        //remove the first background image if there is one
        if (elements.isNotEmpty() && elements[0].__typename == "ImageType") {
            elements = elements.drop(1)
        }
        val horizontalAdapter = CategoryAdapter(elements)
        holder.horizontalRecyclerView.adapter = horizontalAdapter
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}