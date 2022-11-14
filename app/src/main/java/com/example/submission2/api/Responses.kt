package com.example.submission2.api

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

data class FileUploadResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class RegisterResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val LoginResult: LoginResult?
)

data class LoginResult(
    @field:SerializedName("userId")
    var userId: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("token")
    var token: String
)

data class AllStoriesResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val ListStory: List<ListStory>
)

@Parcelize
data class ListStory(
    @PrimaryKey
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var photoUrl: String? = null,
    var createdAt: Date? = null,
    var lat: Double,
    var lon: Double
) : Parcelable

data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val isLogin: Boolean
)










