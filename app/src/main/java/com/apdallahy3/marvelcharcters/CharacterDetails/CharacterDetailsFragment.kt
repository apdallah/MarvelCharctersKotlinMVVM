package com.apdallahy3.marvelcharcters.CharacterDetails


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apdallahy3.marvelcharcters.Network.Models.CharacterItem
import com.apdallahy3.marvelcharcters.Network.DetailsModel.DetailsItem

import com.apdallahy3.marvelcharcters.R
import com.apdallahy3.marvelcharcters.databinding.FragmentCharacterDetailsBinding


class CharacterDetailsFragment : Fragment() {
    lateinit var characterItem: CharacterItem
    lateinit var binding: FragmentCharacterDetailsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCharacterDetailsBinding>(
            inflater,
            R.layout.fragment_character_details,
            container,
            false
        )
        val application = requireNotNull(activity).application
        //get selected character from arguments
        characterItem = CharacterDetailsFragmentArgs.fromBundle(arguments!!).selectedCharacter
        //create a viewmodel factory
        val viewModelFactory = CharacterDetailsViewModelFactory(characterItem, application)
        //init our details viewmodel
        binding.viewModel = ViewModelProviders.of(
            this
            , viewModelFactory
        ).get(characterDetailsViewModel::class.java)
        //bind dynamic info
        if (!characterItem.description.isEmpty()) {
            addDescriptionView(binding.sections as ViewGroup, characterItem.description)
        }
        binding.viewModel!!.comics.observe(this, Observer {
            if(it.size>0){
                addSectionView(binding.sections as ViewGroup, "Comics", it)

            }
        })
        binding.viewModel!!.series.observe(this, Observer {
            if(it.size>0){
                addSectionView(binding.sections as ViewGroup, "Series", it)

            }
        })
        binding.viewModel!!.stories.observe(this, Observer {
            if(it.size>0){
                addSectionView(binding.sections as ViewGroup, "Stories", it)

            }
        })
        binding.viewModel!!.events.observe(this, Observer {
            if(it.size>0){
                addSectionView(binding.sections as ViewGroup, "Events", it)

            }

        })
        bindDaynamicInfo(binding.relatedlinks as ViewGroup)

        return binding.root
    }

    fun bindDaynamicInfo(parent: ViewGroup) {



        if (characterItem.urls.size > 0) {
            addRelatedLinksView(parent)
            for (item in characterItem.urls) {

                val flag: Boolean = characterItem.urls.indexOf(item) == characterItem.urls.lastIndex
                addRelatedLinkItemView(parent, item.type, item.url, flag)

            }
        }
    }

    fun addDescriptionView(parent: ViewGroup, description: String) {

        //description name TextView
        val descriptionTextViewName = TextView(context)
        val nameTVParams = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        nameTVParams.setMargins(20, 20, 0, 20)
         descriptionTextViewName.layoutParams = nameTVParams
        descriptionTextViewName.setTextColor(Color.RED)
        descriptionTextViewName.setText(getString(R.string.description_title))
        //description name TextView
        val descriptionTextViewValue = TextView(context)
        val valueTVParams = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        valueTVParams.setMargins(20, 20, 0, 20)
        descriptionTextViewValue.layoutParams = valueTVParams
        descriptionTextViewValue.setTextColor(Color.WHITE)
        descriptionTextViewValue.setText(description)

        //add Layout container to main layout
        parent.addView(descriptionTextViewName)
        parent.addView(descriptionTextViewValue)


    }

    fun addSectionView(parent: ViewGroup, sectionTitle: String, sectionItems: List<DetailsItem>) {
        //SectionTitle name TextView
        val sectionTitleTV = TextView(context)
        val sectionTitleTVParams = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        sectionTitleTVParams.setMargins(20, 20, 0, 20)
        sectionTitleTV.layoutParams = sectionTitleTVParams
        sectionTitleTV.setTextColor(Color.RED)
        sectionTitleTV.setText(sectionTitle)
        //AddRecyclerView
        val recyclerView = RecyclerView(context!!)
        val adapter = SectionAdapter(sectionItems)
        val recyclerViewParams = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        recyclerViewParams.setMargins(20, 20, 0, 20)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        recyclerView.layoutParams = recyclerViewParams
        recyclerView.adapter = adapter

        parent.addView(sectionTitleTV)
        parent.addView(recyclerView)

    }

    fun addRelatedLinksView(parent: ViewGroup) {

        //description name TextView
        val RelatedLinksTextView = TextView(context)
        val nameTVParams = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        nameTVParams.setMargins(20, 20, 0, 20)
        RelatedLinksTextView.layoutParams = nameTVParams
        RelatedLinksTextView.setTextColor(Color.RED)
        RelatedLinksTextView.setText(getString(R.string.related_links_title))
        parent.addView(RelatedLinksTextView)


    }

    fun addRelatedLinkItemView(parent: ViewGroup, linkTitle: String, linkUrl: String, lastItem: Boolean) {
        //container Layout
        val detailsLayout = RelativeLayout(context)
        val detilsLayoutParams = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        detilsLayoutParams.setMargins(20, 32, 0, 0)
        detailsLayout.layoutParams = detilsLayoutParams

        //Details name TextView
        val detailsTextViewName = TextView(context)
        val nameTVParams = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        nameTVParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        detailsTextViewName.layoutParams = nameTVParams
        detailsTextViewName.setTextColor(Color.WHITE)
        detailsTextViewName.setText(linkTitle)
        //Details go to arrow ImageView
        val arrowImageView = ImageView(context)
        val arrowParams = RelativeLayout.LayoutParams(
            64, 64
        )
        arrowParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        arrowImageView.layoutParams = arrowParams
        arrowImageView.setImageResource(R.drawable.ic_go)
        //Line View
        val lineView = View(context)
        val lineParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2)
        lineParam.setMargins(20, 20, 20, 20)
        lineView.setBackgroundColor(Color.LTGRAY)
        lineView.layoutParams = lineParam

        //add subviews to container layout
        detailsLayout.addView(detailsTextViewName)
        detailsLayout.addView(arrowImageView)

        //add Layout container to main layout
        parent.addView(detailsLayout)
        if (!lastItem)
            parent.addView(lineView)


    }


}
