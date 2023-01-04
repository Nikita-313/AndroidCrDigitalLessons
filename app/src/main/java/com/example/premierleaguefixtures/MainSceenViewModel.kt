package com.example.premierleaguefixtures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.models.Match
import com.example.premierleaguefixtures.network.APIForMatches
import com.example.premierleaguefixtures.repository.MatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainScreenViewModel(
    private val matchesRepository: MatchesRepository = MatchesRepository()
) : ViewModel() {

    private val TAG = "MainScreenViewModel"
    private val _matches = MutableStateFlow(emptyList<Match>())
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fixturedownload.com/feed/json/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val networkService = retrofit.create(APIForMatches::class.java)

    fun getMatches(): StateFlow<List<Match>> = _matches

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = networkService.matches()
                if (response.isSuccessful){
                    val matchesFromServer = response.body()
                    if (matchesFromServer != null) {
                        _matches.emit(matchesFromServer)
                    }
                } else Log.e(TAG,"Error! " + response.code().toString())
            } catch (e: Exception){
                Log.e(TAG,e.toString())
            }
        }
    }
}