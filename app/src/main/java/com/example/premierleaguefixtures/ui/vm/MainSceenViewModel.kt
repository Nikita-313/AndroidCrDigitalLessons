package com.example.premierleaguefixtures.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.usecase.GetSavedMatchesUseCase
import com.example.premierleaguefixtures.domain.usecase.LoadMatchesUseCase
import com.example.premierleaguefixtures.domain.usecase.SearchMatchesByTeamNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val loadMatchesUseCase: LoadMatchesUseCase,
    private val getSavedMatchesUseCase: GetSavedMatchesUseCase,
    private val searchMatchesByTeamNameUseCase: SearchMatchesByTeamNameUseCase
) : ViewModel() {

    private val _matches = MutableStateFlow(emptyList<FootballMatch>())
    private val _isFetchingData = MutableStateFlow(true)
    private val _searchEditText = MutableStateFlow("")
    val searchEditText = _searchEditText.asStateFlow()

    fun setSearchEditText(searchEditText: String) {
        _searchEditText.value = searchEditText
        searchMatchesByTeamName(searchEditText)
    }

    fun getMatches(): StateFlow<List<FootballMatch>> = _matches
    fun getIsFetchingData(): StateFlow<Boolean> = _isFetchingData

    init {
        getLocalData()
        fetchServerData()
    }

    private fun getLocalData() {
        viewModelScope.launch(Dispatchers.IO) {
            getSavedMatchesUseCase.execute().collect{
                _matches.emit(it)
            }
        }
    }

    fun fetchServerData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFetchingData.emit(true)
            val list = loadMatchesUseCase.execute();
            if(list.isNotEmpty())
            _matches.emit(list)
            _isFetchingData.emit(false)
        }
    }

    private fun searchMatchesByTeamName(teamName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchMatchesByTeamNameUseCase.execute(teamName).collect{
                _matches.emit(it)
            }
        }
    }

}