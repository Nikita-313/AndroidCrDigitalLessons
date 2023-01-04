package com.example.premierleaguefixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.repository.Match
import com.example.premierleaguefixtures.repository.MatchesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val matchesRepository: MatchesRepository = MatchesRepository()
) : ViewModel() {

    private val _matches = MutableStateFlow(emptyList<Match>())
    fun getMatches(): StateFlow<List<Match>> = _matches


    init {
        viewModelScope.launch {
            matchesRepository.getFootballMatches().collect{
                _matches.emit(it)
            }
        }
    }
}