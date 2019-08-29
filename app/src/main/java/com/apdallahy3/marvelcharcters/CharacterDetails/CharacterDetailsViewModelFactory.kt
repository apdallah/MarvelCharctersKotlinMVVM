package com.apdallahy3.marvelcharcters.CharacterDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apdallahy3.marvelcharcters.Network.Models.CharacterItem

class CharacterDetailsViewModelFactory(
    private val characterItem: CharacterItem,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(characterDetailsViewModel::class.java)) {
            return characterDetailsViewModel(characterItem, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}