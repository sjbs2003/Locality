package com.example.locality.model

import com.example.locality.utils.ApiConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

interface AppContainer {
    val repository: EventRepository
}

class DefaultAppContainer: AppContainer {

    private val json = Json { ignoreUnknownKeys = true }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original: Request = chain.request()
            val request: Request = original.newBuilder()
                .header("Authorization", ApiConfig.getAuthHeader())
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }.build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(ApiConfig.BASE_URL)
        .client(client)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val repository: EventRepository by lazy {
        EventRepository(retrofitService)
    }
}