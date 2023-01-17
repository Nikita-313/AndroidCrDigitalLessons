package com.example.premierleaguefixtures.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.usecase.GetMatchByNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getMatchByNumberUseCase: GetMatchByNumberUseCase
) : ViewModel() {

    private val _match = MutableStateFlow<FootballMatch?>(null)
    fun getMatch(): StateFlow<FootballMatch?> = _match

    fun getMatchByNumber(number: Int) {
        getMatchByNumberUseCase.execute(number)
            .onEach { _match.emit(it) }
            .launchIn(viewModelScope)
    }

}