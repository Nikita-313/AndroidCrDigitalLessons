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
class DetailsScreenViewModel @Inject constructor(
    private val matchesLR: MatchesLocalRepository,
) : ViewModel() {

    private val _match = MutableStateFlow<Match?>(null)
    fun getMatch(): StateFlow<Match?> = _match

    fun getMatchByNumber(number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            matchesLR.getMatchByNumber(number).collect {
                _match.emit(it)
            }
        }
    }

}