package com.example.submission2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission2.preferences.UserPreference
import com.example.submission2.login.LoginViewModel
import com.example.submission2.main.MainViewModel
import com.example.submission2.register.RegisterViewModel
import com.example.submission2.story.allstory.AllStoryViewModel

class ViewModelFactory(
    private val pref: UserPreference,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }

            modelClass.isAssignableFrom(AllStoryViewModel::class.java) -> {
                AllStoryViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}