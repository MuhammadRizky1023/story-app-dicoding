package com.example.submission2.story.allstory

import android.util.Log
import androidx.lifecycle.*
import com.example.submission2.api.*
import com.example.submission2.preferences.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllStoryViewModel(private val pref: UserPreference) : ViewModel() {
    private val _listStories = MutableLiveData<List<ListStory>>()
    private val listStories: LiveData<List<ListStory>> = _listStories

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getStoriesPaging() = listStories

    fun getAllStories(token: String) {
        val client = ApiConfig().getApiService().allStoriesLoc(token)
        client.enqueue(object : Callback<AllStoriesResponse> {
            override fun onResponse(
                call: Call<AllStoriesResponse>,
                response: Response<AllStoriesResponse>
            ) {
                if (response.isSuccessful) {
                    _listStories.postValue(response.body()?.ListStory)
                }
            }

            override fun onFailure(call: Call<AllStoriesResponse>, t: Throwable) {
                Log.e(TAG, "OnFailure : ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "AllStoriesViewModel"
    }
}
