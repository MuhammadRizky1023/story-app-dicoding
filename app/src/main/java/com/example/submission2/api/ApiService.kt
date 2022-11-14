package com.example.submission2.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<FileUploadResponse>

    @FormUrlEncoded
    @POST("register")
    fun onRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun doLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun allStoriesPaging(
        @Header("Authorization") authToken: String,
        @Query("page") page: Int? = 0,
        @Query("size") size: Int? = 10,
    ): AllStoriesResponse

    @GET("stories")
    fun allStoriesLoc(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1,
    ): Call<AllStoriesResponse>
}