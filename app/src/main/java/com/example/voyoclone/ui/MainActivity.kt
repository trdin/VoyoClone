package com.example.voyoclone.ui

import FrontData
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voyoclone.R
import com.example.voyoclone.adapters.CategoryRowAdapter
import com.example.voyoclone.api.ApiManager
import com.example.voyoclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    lateinit var apiManager: ApiManager

    lateinit var categoriesRV: RecyclerView
    lateinit var nestedSV: NestedScrollView

    //data
    var categories: MutableList<FrontData> = mutableListOf()

    var loadedPart = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        apiManager = ApiManager()
        categoriesRV = _binding.RVCategories
        nestedSV = _binding.NestedSv

        setContentView(_binding.root)

        getCategories()
        initScrollEvent()

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
                        Toast.makeText(this, R.string.no_data, Toast.LENGTH_LONG).show()
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
                if (throwable != null && !isInternetAvailable(this)) {
                    errorMessage = R.string.error_no_internet
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            })
    }

    private fun initCategoriesRV() {
        val movieAdapter = CategoryRowAdapter(this,categories)
        categoriesRV.adapter = movieAdapter
        categoriesRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

}