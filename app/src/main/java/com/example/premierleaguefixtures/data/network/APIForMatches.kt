package com.example.premierleaguefixtures.data.network

import com.example.premierleaguefixtures.data.model.Match
import retrofit2.Response
import retrofit2.http.GET


interface APIForMatches {
    @GET("epl-2021")
    suspend fun matches(): Response<List<Match>>
}