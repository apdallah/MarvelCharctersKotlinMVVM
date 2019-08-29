package com.apdallahy3.marvelcharcters.allCharacters


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.apdallahy3.marvelcharcters.R
import com.apdallahy3.marvelcharcters.databinding.FragmentAllCharactersBinding

class AllCharactersFragment : Fragment() {
    private val characterViewModel: charactersViewModel  by lazy {
        ViewModelProviders.of(this).get(charactersViewModel::class.java)
    }

    private var menu: Menu? = null
    private var preLast: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAllCharactersBinding>(
            inflater,
            R.layout.fragment_all_characters,
            container,
            false
        )
        binding.setLifecycleOwner(this)
        binding.viewmodel = characterViewModel
        binding.charactersList.adapter = CharactersAdapter(characterViewModel, CharactersAdapter.OnClickListener {
            characterViewModel.displayCharacterDetails(it)

        })
        //create scroll listener to load more data when reach recyclerview end
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutmanager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = recyclerView.layoutManager!!.childCount
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val lastItem = layoutmanager.findFirstVisibleItemPosition() + visibleItemCount

                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        //to avoid multiple calls for last item
                        preLast = lastItem
                        //Load More Data
                        characterViewModel.loadMoreCharacters(characterViewModel.characterList.value!!.size)

                    }
                }


            }
        }
        //set scroll listener to recyclerview
        binding.charactersList.addOnScrollListener(scrollListener)

        //observe view type which will be change using menu item and update adapter
        characterViewModel.viewType.observe(this, Observer {
            (binding.charactersList.adapter as CharactersAdapter).notifyDataSetChanged()
        })
        //observe selectedCharacter live data which will be changed on recycler item clicked
        characterViewModel.navigateToSelectedCharacter.observe(this, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        AllCharactersFragmentDirections
                            .actionAllCharactersFragmentToCharacterDetailsFragment(it)
                    )
                characterViewModel.displayCharacterDetailsComplete()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
        inflater!!.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_grid_list) {
            when (characterViewModel.viewType.value) {
                1 -> {
                    menu!!.get(0).setIcon(R.drawable.grid_icon)
                    characterViewModel.viewType.value = 2
                }
                2 -> {
                    menu!!.get(0).setIcon(R.drawable.list_icon)

                    characterViewModel.viewType.value = 1

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
