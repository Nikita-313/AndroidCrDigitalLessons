package com.example.premierleaguefixtures.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.usecase.GetMatchByNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getMatchByNumberUseCase: GetMatchByNumberUseCase
) : ViewModel() {

    private val _match = MutableStateFlow<FootballMatch?>(null)
    fun getMatch(): StateFlow<FootballMatch?> = _match

    fun getMatchByNumber(number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getMatchByNumberUseCase.execute(number).collect{
                _match.emit(it)
            }
        }
    }

}