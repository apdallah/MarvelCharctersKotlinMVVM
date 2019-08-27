package com.apdallahy3.marvelcharcters.allCharacters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apdallahy3.marvelcharcters.BuildConfig
import com.apdallahy3.marvelcharcters.Network.MarvelAPI
import com.apdallahy3.marvelcharcters.Network.Models.CharacterProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import javax.security.auth.callback.Callback

class charactersViewModel : ViewModel() {

    //make a job to be used with coroutines
    private var viewModelJob = Job()
    //set coroutines scope
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    //encapsulate character list can't be changed from fragment
    private val _charactersList = MutableLiveData<List<CharacterProperty>>()
    //make a read only character list to be read from fragment
    val characterList: LiveData<List<CharacterProperty>>
        get() = _charactersList

    private val _statues = MutableLiveData<String>()

    val statues: LiveData<String>
        get() = _statues

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {

         coroutineScope.launch {
            var getCharactersDeferred =
                MarvelAPI.retrofitService.getCharacters(
                    BuildConfig.API_KEY_PRIVATE
                    , BuildConfig.API_HASH
                    , "1"
                )
            try {
                var listResult = getCharactersDeferred.await()
                  if (listResult.size > 0) {

                    _charactersList.value = listResult
                    _statues.value=listResult.size.toString()
                 }

            } catch (e: Exception) {

                _statues.value = "Failure: ${e.message}"
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}