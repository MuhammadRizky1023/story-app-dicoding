package com.example.submission2.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission2.api.UserModel
import com.example.submission2.preferences.UserPreference
import kotlinx.coroutines.launch

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}