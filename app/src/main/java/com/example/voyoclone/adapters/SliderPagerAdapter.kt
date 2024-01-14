package com.example.voyoclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.voyoclone.R
import com.example.voyoclone.databinding.SliderItemBinding
import com.example.voyoclone.models.FrontPayload
import com.squareup.picasso.Picasso
import timber.log.Timber

class SliderPagerAdapter(private val context: Context, private val slideList: List<FrontPayload>) : PagerAdapter() {

    private var _binding: SliderItemBinding? = null

    override fun getCount(): Int {
        return slideList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        _binding = SliderItemBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val slideImg: ImageView = _binding!!.slideImg
        val slideText: TextView = _binding!!.slideTitle
        val decriptionText: TextView = _binding!!.description

        val item = slideList[position]
        var image = ""
        slideText.text = item.title
        decriptionText.text = item.description ?: ""

        if (item.portraitImage != null) {
            image = item.portraitImage.src ?: ""

        } else if (item.image != null) {
            image = item.image.src ?: ""
        }

        if (image.isNotBlank()) {
            //replacing the placeholder in the URL
            image = image.replace("PLACEHOLDER", "298x441")
            Picasso.get().load(image).error(R.drawable.default_category_image).into(slideImg)
        }else{
            Timber.tag("ImageLoading").e("Empty image URL for position $position")
            slideImg.setImageResource(R.drawable.default_category_image)
        }

        container.addView(_binding!!.root)
        return _binding!!.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


}