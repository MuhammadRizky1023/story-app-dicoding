package com.example.submission2.story.pagingstory

import android.content.Context
import com.example.submission2.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig().getApiService()
        return StoryRepository(apiService, context)
    }
}