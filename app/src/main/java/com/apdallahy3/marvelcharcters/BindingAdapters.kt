package com.apdallahy3.marvelcharcters

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apdallahy3.marvelcharcters.Network.Models.CharacterItem
import com.apdallahy3.marvelcharcters.allCharacters.CharactersAdapter
import com.apdallahy3.marvelcharcters.allCharacters.MarvelAPIStatus
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("recyclerData")
fun bindRecyclerView(recycler: RecyclerView, list: List<CharacterItem>?) {
    val adapter = recycler.adapter as CharactersAdapter
    adapter.submitList(list)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = (imgUrl ).toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_img)
                     .error(R.drawable.ic_broken_image)
             )
            .into(imgView)
    }
}

//@BindingAdapter("imageUrl")
//fun bindImageToScrollView(imgView: ScrollView, imgUrl: String?) {
//    imgUrl?.let {
//        val imgUri = (imgUrl ).toUri().buildUpon().scheme("https").build()
//        Glide.with(imgView.context)
//            .load(imgUri)
//            .addListener(object:R)
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.loading_animation)
//                    .error(R.drawable.ic_broken_image)
//            )
//
//    }
//}

@BindingAdapter("apiStatues")
fun bindApiStatus(statusImageView: ImageView, statues: MarvelAPIStatus?) {
    Log.i("Binding",""+statues)
    when (statues) {
        MarvelAPIStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading)
        }
        MarvelAPIStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)

        }
        MarvelAPIStatus.DONE -> {
            statusImageView.visibility = View.GONE

        }
    }
}