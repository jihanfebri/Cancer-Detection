package com.dicoding.asclepius.data.remote.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val API_KEY = "388ae0e212684b53a654c3c73202643e"

        fun getApiService(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val url = original.url.newBuilder().addQueryParameter("apiKey", API_KEY).build()
                    val request = original.newBuilder().url(url).build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }
}
