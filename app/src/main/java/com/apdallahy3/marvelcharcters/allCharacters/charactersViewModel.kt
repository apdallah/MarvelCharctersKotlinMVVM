package com.apdallahy3.marvelcharcters.allCharacters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apdallahy3.marvelcharcters.BuildConfig
import com.apdallahy3.marvelcharcters.Network.MarvelAPI
import com.apdallahy3.marvelcharcters.Network.Models.CharacterItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class MarvelAPIStatus { LOADING, ERROR, DONE }

class charactersViewModel : ViewModel() {

    //make a job to be used with coroutines
    private var viewModelJob = Job()
    //set coroutines scope
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    //encapsulate character list can't be changed from fragment
    private val _charactersList = MutableLiveData<List<CharacterItem>>()
    //make a read only character list to be read from fragment
    val characterList: LiveData<List<CharacterItem>>
        get() = _charactersList

    //Call Statues
    private val _statues = MutableLiveData<MarvelAPIStatus>()
    val statues: LiveData<MarvelAPIStatus>
        get() = _statues
    // layout type in recyclerview
    val viewType = MutableLiveData<Int>()
    //navigationto selected Character
    private val _navigateToSelectedCharacter = MutableLiveData<CharacterItem>()

    val navigateToSelectedCharacter: LiveData<CharacterItem>
        get() = _navigateToSelectedCharacter

    init {
        viewType.value = 1
        getAllCharacters()
    }

    private fun getAllCharacters() {

        coroutineScope.launch {
            var getCharactersDeferred =
                MarvelAPI.retrofitService.getCharacters(
                    BuildConfig.API_KEY_PUBLIC
                    , BuildConfig.API_HASH
                    , "1", 0
                )
            try {
                _statues.value = MarvelAPIStatus.LOADING

                var listResult = getCharactersDeferred.await()
                if (listResult.data.results.size > 0) {

                    _charactersList.value = listResult.data.results
                    _statues.value = MarvelAPIStatus.DONE
                }

            } catch (e: Exception) {

                _statues.value = MarvelAPIStatus.ERROR
                _charactersList.value = ArrayList()
            }
        }

    }

    fun loadMoreCharacters(offset: Int) {
        Log.i("LOADMORE", "" + offset)
        coroutineScope.launch {
            var getCharactersDeferred =
                MarvelAPI.retrofitService.getCharacters(
                    BuildConfig.API_KEY_PUBLIC
                    , BuildConfig.API_HASH
                    , "1", offset
                )
            try {
//                _statues.value = MarvelAPIStatus.LOADING

                var listResult = getCharactersDeferred.await()
                if (listResult.data.results.size > 0) {

                    _charactersList.value = _charactersList.value?.plus(listResult.data.results)
                    //                    _statues.value = MarvelAPIStatus.DONE
                }

            } catch (e: Exception) {

//                _statues.value = MarvelAPIStatus.ERROR
//                _charactersList.value = ArrayList()
            }
        }
    }

    fun displayCharacterDetails(characterItem: CharacterItem) {
        _navigateToSelectedCharacter.value = characterItem
    }
    fun displayCharacterDetailsComplete() {
        _navigateToSelectedCharacter.value = null
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}