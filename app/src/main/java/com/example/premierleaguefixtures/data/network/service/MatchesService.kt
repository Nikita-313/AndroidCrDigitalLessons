package com.example.premierleaguefixtures.data.network.service

import com.example.premierleaguefixtures.data.network.models.FootballMatchNetwork

import retrofit2.http.GET


interface MatchesService {
    @GET("epl-2021")
    suspend fun matches(): List<FootballMatchNetwork>
}