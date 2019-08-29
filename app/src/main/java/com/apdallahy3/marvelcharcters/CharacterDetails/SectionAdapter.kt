package com.apdallahy3.marvelcharcters.CharacterDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.apdallahy3.marvelcharcters.Network.DetailsModel.DetailsItem
import com.apdallahy3.marvelcharcters.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SectionAdapter(private val sectionItems:List<DetailsItem>) : RecyclerView.Adapter<SectionAdapter.SectionViewHoler>() {

    class SectionViewHoler(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val imageView=itemView.findViewById(R.id.photo) as ImageView
        val title=itemview.findViewById(R.id.title) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHoler {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.section_item, parent, false)
        return SectionViewHoler(viewItem)    }

    override fun getItemCount(): Int {
        return sectionItems.size
    }

    override fun onBindViewHolder(holder: SectionViewHoler, position: Int) {
        val item=sectionItems.get(position)
        holder.title.text=item.title
        val imgUri = (item.thumbnail.path+"/portrait_small."+item.thumbnail.extension ).toUri().buildUpon().scheme("https").build()

        Glide.with(holder.imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.ic_broken_image)
            )
            .into(holder.imageView)
    }
}
