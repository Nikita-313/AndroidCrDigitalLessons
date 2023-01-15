package com.example.premierleaguefixtures.data.network

import com.example.premierleaguefixtures.domain.models.FootballMatch
import retrofit2.Response
import retrofit2.http.GET


interface MatchesService {
    @GET("epl-2021")
    suspend fun matches():Response<List<FootballMatch>>
}