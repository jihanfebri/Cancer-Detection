package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.HealthArticles
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String = "cancer",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("language") language: String = "en",
        @Query("category") category: String = "health"
    ): Call<HealthArticles>
}
