package com.example.voyoclone.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.voyoclone.R
import com.example.voyoclone.databinding.FragmentDetailsBinding
import com.example.voyoclone.models.FrontPayload
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import timber.log.Timber


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    private var data: FrontPayload? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)



        if (arguments != null) {
            binding.title.text = arguments?.getString("typename")
            val frontDataString = arguments?.getString("data")
            Timber.tag("Details").d(frontDataString)

            if (frontDataString != null) {
                try {
                    val gson = Gson()
                    val frontData = gson.fromJson(frontDataString, FrontPayload::class.java)
                    if (frontData != null) {
                        data = frontData
                        Timber.tag("Details").d("hi")
                        binding.title.text = data?.title
                        binding.description.text = data?.description
                        var image = ""
                        if (data?.portraitImage != null) {
                            image = data!!.portraitImage?.src ?: ""

                        } else if (data?.image != null) {
                            image = data!!.image?.src ?: ""
                        }
                        image = image.replace("PLACEHOLDER", "298x441")
                        Timber.tag("Details").d(image)
                        Picasso.get().load(image).error(R.drawable.default_category_image).into(binding.DetailsImageView)

                    }
                } catch (t: Throwable) {
                    Timber.tag("details").d(t)
                }
            }
        }


        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}