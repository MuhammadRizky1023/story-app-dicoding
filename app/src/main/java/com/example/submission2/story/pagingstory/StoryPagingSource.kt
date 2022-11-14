package com.example.submission2.story.pagingstory

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submission2.api.ApiService
import com.example.submission2.api.ListStory
import com.example.submission2.preferences.LoginSession

class StoryPagingSource(private val apiService: ApiService, context: Context) :
    PagingSource<Int, ListStory>() {

    private var loginSession: LoginSession = LoginSession(context)

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStory> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX

            val token = loginSession.passToken().toString()
            val responseData =
                apiService.allStoriesPaging("Bearer $token", position, params.loadSize)
            val data = responseData.ListStory
            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}