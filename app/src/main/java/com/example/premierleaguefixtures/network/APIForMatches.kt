package com.example.premierleaguefixtures.network

import com.example.premierleaguefixtures.models.Match
import retrofit2.Response
import retrofit2.http.GET


interface APIForMatches {
    @GET("epl-2021")
    suspend fun matches(): Response<List<Match>>
}