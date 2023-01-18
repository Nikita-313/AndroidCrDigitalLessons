package com.example.premierleaguefixtures.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.usecase.LoadMatchesUseCase
import com.example.premierleaguefixtures.domain.usecase.GetMatchesByTeamNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val loadMatchesUseCase: LoadMatchesUseCase,
    private val getMatchesByTeamNameUseCase: GetMatchesByTeamNameUseCase
) : ViewModel() {

    private val _matches = MutableStateFlow(emptyList<FootballMatch>())
    private val _isFetchingData = MutableStateFlow(false)
    private val _isSearchingData = MutableStateFlow(false)
    private val _searchEditText = MutableStateFlow("")
    val searchEditText = _searchEditText.asStateFlow()

    fun setSearchEditText(searchEditText: String) {
        _searchEditText.value = searchEditText
    }

    fun getMatches(): StateFlow<List<FootballMatch>> = _matches
    fun getIsFetchingData(): StateFlow<Boolean> = _isFetchingData

    fun getIsSearchingData(): StateFlow<Boolean> = _isSearchingData
    fun setIsSearchingData(b: Boolean) {
        _isSearchingData.value = b
    }

    init {
        getLocalData()
        fetchServerData()
    }

    private fun getLocalData() {
        _searchEditText
            .debounce(200)
            .onEach(::getMatchesByTeamName)
            .launchIn(viewModelScope)
    }

    fun fetchServerData() {
        loadMatchesUseCase.execute()
            .onStart { _isFetchingData.emit(true) }
            .onCompletion { _isFetchingData.emit(false) }
            .launchIn(viewModelScope)
    }

    private fun getMatchesByTeamName(teamName: String) {
        getMatchesByTeamNameUseCase.execute(teamName)
            .onEach {
                _matches.emit(it)
            }
            .launchIn(viewModelScope)
    }

}