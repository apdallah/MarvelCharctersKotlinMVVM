package com.apdallahy3.marvelcharcters.allCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
 import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apdallahy3.marvelcharcters.Network.Models.CharacterItem
import com.apdallahy3.marvelcharcters.databinding.CharcterGridItemBinding
import com.apdallahy3.marvelcharcters.databinding.CharcterListItemBinding

class CharactersAdapter(val viewModel: charactersViewModel, val onClickListener: OnClickListener) :
    ListAdapter<CharacterItem, RecyclerView.ViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<CharacterItem>() {
        override fun areContentsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean {
            return oldItem == newItem
        }

    }

    class ListViewHolder(private var binding: CharcterListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterItem) {
            binding.characterItem = item
            binding.executePendingBindings()
        }
    }

    class GridViewHolder(private var binding: CharcterGridItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: CharacterItem) {

            binding.characterItem = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            1 -> viewHolder = GridViewHolder(CharcterGridItemBinding.inflate(LayoutInflater.from(parent.context)))
            2 -> viewHolder = ListViewHolder(CharcterListItemBinding.inflate(LayoutInflater.from(parent.context)))
            else -> {
                viewHolder = GridViewHolder(CharcterGridItemBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        if (holder is GridViewHolder) {
            holder.bind(item) as?GridViewHolder
        } else if (holder is ListViewHolder) {
            holder.bind(item) as?ListViewHolder

        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewModel.viewType.value!!;
    }

    class OnClickListener(val clickListener: (charcterItem: CharacterItem) -> Unit) {
        fun onClick(charcterItem: CharacterItem) = clickListener(charcterItem)
    }

}

