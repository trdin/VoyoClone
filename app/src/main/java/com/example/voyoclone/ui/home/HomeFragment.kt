package com.example.voyoclone.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voyoclone.R
import com.example.voyoclone.adapters.CategoryRowAdapter
import com.example.voyoclone.api.ApiManager
import com.example.voyoclone.databinding.FragmentHomeBinding
import com.example.voyoclone.models.FrontData
import com.example.voyoclone.ui.MainActivity
import timber.log.Timber


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    lateinit var apiManager: ApiManager

    lateinit var categoriesRV: RecyclerView
    lateinit var nestedSV: NestedScrollView

    //data
    var categories: MutableList<FrontData> = mutableListOf()

    var loadedPart = 1

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        apiManager = ApiManager()
        categoriesRV = _binding!!.RVCategories
        nestedSV = _binding!!.NestedSv

        if(categories.size == 0){
            getCategories()
        }else{
            initCategoriesRV()
        }
        initScrollEvent()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initScrollEvent() {
        // source : https://www.geeksforgeeks.org/android-recyclerview-load-more-on-scroll-with-example/
        nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (loadedPart <= 3) {
                    getCategories()
                }
            }
        })
    }

    @SuppressLint("BinaryOperationInTimber")
    private fun getCategories() {
        val query = "onl_web_basic_front(url: \"/\" siteId: 30005 part:$loadedPart)"
        apiManager.fetchData(query,
            onResponse = { response ->
                // Handle the response
                response?.let {
                    val data = it.data
                    if (data.front != null) {
                        categories += data.front.data.filter { element -> element.payload.isNotEmpty() && element.itemTypes != "epg" }
                        initCategoriesRV()
                        if (loadedPart > 1) {
                            nestedSV.post {
                                nestedSV.smoothScrollBy(0, 300)
                            }
                        }
                        loadedPart++

                    }else{
                        Toast.makeText(activity, R.string.no_data, Toast.LENGTH_LONG).show()
                    }
                }
            },
            onFailure = { throwable, code ->

                var errorMessage = when (code) {
                    404 -> R.string.error_not_found
                    500 -> R.string.error_internal_server
                    else -> R.string.error_unknown
                }

                //check if there is no internet connection and then notify the user accordingly
                if (throwable != null && !(activity as MainActivity).isInternetAvailable(activity as MainActivity)) {
                    errorMessage = R.string.error_no_internet
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                Timber.tag("Api Failure").d(throwable)
            })
    }

    private fun initCategoriesRV() {
        val movieAdapter = CategoryRowAdapter(activity as MainActivity,categories)
        categoriesRV.adapter = movieAdapter
        categoriesRV.layoutManager = LinearLayoutManager(activity as MainActivity, LinearLayoutManager.VERTICAL, false)
    }

}