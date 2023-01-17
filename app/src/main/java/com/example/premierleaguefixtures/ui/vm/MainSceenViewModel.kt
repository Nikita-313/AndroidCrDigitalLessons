package com.example.premierleaguefixtures.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.usecase.GetSavedMatchesUseCase
import com.example.premierleaguefixtures.domain.usecase.LoadMatchesUseCase
import com.example.premierleaguefixtures.domain.usecase.SearchMatchesByTeamNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val loadMatchesUseCase: LoadMatchesUseCase,
    private val getSavedMatchesUseCase: GetSavedMatchesUseCase,
    private val searchMatchesByTeamNameUseCase: SearchMatchesByTeamNameUseCase
) : ViewModel() {

    private val _matches = MutableStateFlow(emptyList<FootballMatch>())
    private val _isFetchingData = MutableStateFlow(false)
    private val _isSearchingData = MutableStateFlow(false)
    private val _searchEditText = MutableStateFlow("")
    val searchEditText = _searchEditText.asStateFlow()

    fun setSearchEditText(searchEditText: String) {
        _searchEditText.value = searchEditText
        searchMatchesByTeamName(searchEditText)
    }

    fun getMatches(): StateFlow<List<FootballMatch>> = _matches
    fun getIsFetchingData(): StateFlow<Boolean> = _isFetchingData

    fun getIsSearchingData(): StateFlow<Boolean> = _isSearchingData
    fun setIsSearchingData(b: Boolean){
        _isSearchingData.value = b
    }

    init {
        getLocalData()
        fetchServerData()
    }

    private fun getLocalData() {
        getSavedMatchesUseCase.execute()
            .onEach { _matches.emit(it) }
            .launchIn(viewModelScope)
    }

    fun fetchServerData() {
        loadMatchesUseCase.execute()
            .onStart { _isFetchingData.emit(true) }
            .onEach { _matches.emit(it) }
            .onCompletion { _isFetchingData.emit(false) }
            .launchIn(viewModelScope)
    }

    private fun searchMatchesByTeamName(teamName: String) {
        searchMatchesByTeamNameUseCase.execute(teamName)
            .onEach { _matches.emit(it) }
            .launchIn(viewModelScope)
    }

}