package com.serviciotecnico.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Si pruebas en un emulador y tu servidor está en la misma máquina, usa 10.0.2.2
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    // El interceptor nos permite ver el tráfico de red en el Logcat, muy útil para depurar.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Expone públicamente la implementación de nuestra ApiService.
     */
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
