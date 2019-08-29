package com.apdallahy3.marvelcharcters.CharacterDetails

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apdallahy3.marvelcharcters.BuildConfig
import com.apdallahy3.marvelcharcters.Network.Models.CharacterItem
import com.apdallahy3.marvelcharcters.Network.DetailsModel.DetailsItem
import com.apdallahy3.marvelcharcters.Network.MarvelAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class characterDetailsViewModel(characterItem: CharacterItem, application: Application) : ViewModel() {
    //
    private val _selectedCharacter = MutableLiveData<CharacterItem>()
    val selectedCharacter: LiveData<CharacterItem>
        get() = _selectedCharacter
    //make a job to be used with coroutines
    private var viewModelJob = Job()
    //set coroutines scope
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //Comics
    private val _comics = MutableLiveData<List<DetailsItem>>()
    val comics: LiveData<List<DetailsItem>>
        get() = _comics

    //Series
    private val _series = MutableLiveData<List<DetailsItem>>()
    val series: LiveData<List<DetailsItem>>
        get() = _series

    //Comics
    private val _events = MutableLiveData<List<DetailsItem>>()
    val events: LiveData<List<DetailsItem>>
        get() = _events

    //Comics
    private val _stories = MutableLiveData<List<DetailsItem>>()
    val stories: LiveData<List<DetailsItem>>
        get() = _stories

    init {
        _selectedCharacter.value = characterItem
        getCharaterDetails("comics")
        getCharaterDetails("stories")
        getCharaterDetails("series")
        getCharaterDetails("events")

    }

    fun getCharaterDetails(type: String) {
        coroutineScope.launch {
            var getCharactersDeferred =
                MarvelAPI.retrofitService.getDetails(
                    selectedCharacter.value!!.id, type,
                    BuildConfig.API_KEY_PUBLIC
                    , BuildConfig.API_HASH
                    , "1"
                )
            try {

                var listResult = getCharactersDeferred.await()
                if (listResult.data.results.size > 0) {
                    when (type) {
                        "comics" -> _comics.value = listResult.data.results
                        "stories" -> _stories.value = listResult.data.results
                        "series" -> _series.value = listResult.data.results
                        "events" -> _events.value = listResult.data.results
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}