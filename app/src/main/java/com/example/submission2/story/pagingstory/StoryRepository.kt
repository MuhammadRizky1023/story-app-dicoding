package com.example.submission2.story.pagingstory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submission2.api.ApiService
import com.example.submission2.api.ListStory

class StoryRepository(private val apiService: ApiService, private var context: Context) {
    fun getStory(): LiveData<PagingData<ListStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, context)
            }
        ).liveData
    }
}