package com.example.premierleaguefixtures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.data.MatchDatabase
import com.example.premierleaguefixtures.data.model.Match
import com.example.premierleaguefixtures.data.model.toMatch
import com.example.premierleaguefixtures.data.model.toMatchEntity
import com.example.premierleaguefixtures.data.network.APIForMatches
import com.example.premierleaguefixtures.repository.MatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainScreenViewModel(
    private val matchesRepository: MatchesRepository = MatchesRepository()
) : ViewModel() {

    private val TAG = "MainScreenViewModel"
    private val _matches = MutableStateFlow(emptyList<Match>())
    private val _isFetchingData = MutableStateFlow(true)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fixturedownload.com/feed/json/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val networkService = retrofit.create(APIForMatches::class.java)
    private val _searchEditText = MutableStateFlow("")
    val searchEditText = _searchEditText.asStateFlow()

    fun setSearchEditText(searchEditText: String) {
        _searchEditText.value = searchEditText
        searchMatchesByTeamName(searchEditText)
    }

    fun getMatches(): StateFlow<List<Match>> = _matches
    fun getIsFetchingData(): StateFlow<Boolean> = _isFetchingData

    init {
        viewModelScope.launch {
            getLocalData();
        }
        fetchServerData()
    }

    private suspend fun getLocalData() {
        MatchDatabase.getDatabase()?.matchDao()?.getAll()?.collect { list ->
            _matches.emit(list.map { it.toMatch() })
        }
    }

    fun fetchServerData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFetchingData.emit(true)
            try {
                val response = networkService.matches()
                if (response.isSuccessful) {
                    val matchesFromServer = response.body()
                    if (matchesFromServer != null) {
                        _matches.emit(matchesFromServer)
                        _isFetchingData.emit(false)
                        MatchDatabase.getDatabase()?.matchDao()?.clearTable()
                        MatchDatabase.getDatabase()?.matchDao()
                            ?.insertAll(matchesFromServer.map { it.toMatchEntity() })
                    }
                } else {
                    _isFetchingData.emit(false)
                    Log.e(TAG, "Error! " + response.code().toString())
                }
            } catch (e: Exception) {
                _isFetchingData.emit(false)
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun searchMatchesByTeamName(teamName:String){
        viewModelScope.launch {
            MatchDatabase.getDatabase()?.matchDao()?.getMatchesByTeamName(teamName)?.collect { list ->
                _matches.emit(list.map { it.toMatch() })
            }
        }
    }

}