package com.serviciotecnico.data.remote

import com.serviciotecnico.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class Todo(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)

// JSONPlaceholder no tiene un modelo de usuario con roles, así que usaremos un modelo simplificado
data class ApiUser(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val pass: String
)

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    @GET("users")
    suspend fun getUserByUsername(@Query("username") username: String): List<ApiUser>

    @POST("users")
    suspend fun registerUser(@Body request: RegisterRequest): ApiUser
}

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        retrofit.create(ApiService::class.java)
    }
}
