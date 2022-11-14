package com.example.submission2.story.pagingstory

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission2.api.ListStory

class PagingStoriesViewModel(storyRepository: StoryRepository) : ViewModel() {
    val allStory: LiveData<PagingData<ListStory>> =
        storyRepository.getStory().cachedIn(viewModelScope)

    class PagingViewModelFactory(private var context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PagingStoriesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PagingStoriesViewModel(Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}