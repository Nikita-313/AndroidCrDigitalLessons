package com.example.premierleaguefixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.data.local.MatchesLocalRepository
import com.example.premierleaguefixtures.data.model.Match
import com.example.premierleaguefixtures.data.network.MatchesNetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val matchesNR: MatchesNetworkRepository,
    private val matchesLR: MatchesLocalRepository,
) : ViewModel() {

    private val _matches = MutableStateFlow(emptyList<Match>())
    private val _isFetchingData = MutableStateFlow(true)
    private val _searchEditText = MutableStateFlow("")
    val searchEditText = _searchEditText.asStateFlow()

    fun setSearchEditText(searchEditText: String) {
        _searchEditText.value = searchEditText
        searchMatchesByTeamName(searchEditText)
    }

    fun getMatches(): StateFlow<List<Match>> = _matches
    fun getIsFetchingData(): StateFlow<Boolean> = _isFetchingData

    init {
        getLocalData()
        fetchServerData()
    }

    private fun getLocalData() {
        viewModelScope.launch(Dispatchers.IO) {
            matchesLR.getMatches().collect {
                _matches.emit(it)
            }
        }
    }

    fun fetchServerData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFetchingData.emit(true)
            matchesNR.getMatches().collect {
                _matches.emit(it)
                _isFetchingData.emit(false)
            }
            _isFetchingData.emit(false)
        }
    }

    private fun searchMatchesByTeamName(teamName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            matchesLR.searchMatchesByTeamName(teamName).collect {
                _matches.emit(it)
            }
        }
    }

}