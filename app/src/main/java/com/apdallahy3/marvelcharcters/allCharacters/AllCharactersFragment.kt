package com.apdallahy3.marvelcharcters.allCharacters


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.apdallahy3.marvelcharcters.R
import com.apdallahy3.marvelcharcters.databinding.FragmentAllCharactersBinding

class AllCharactersFragment : Fragment() {
    private val characterViewModel: charactersViewModel  by lazy {
        ViewModelProviders.of(this).get(charactersViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAllCharactersBinding>(
            inflater,
            R.layout.fragment_all_characters,
            container,
            false
        )
        binding.setLifecycleOwner(this)

        binding.viewmodel = characterViewModel

        return binding.root
    }


}
