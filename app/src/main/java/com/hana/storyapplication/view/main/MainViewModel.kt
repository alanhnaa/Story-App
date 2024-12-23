package com.hana.storyapplication.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hana.storyapplication.data.UserRepository
import com.hana.storyapplication.data.preference.UserModel
import com.hana.storyapplication.data.response.ListStoryItem

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    var isLoading: LiveData<Boolean> = repository.isLoading

    fun getStory(): LiveData<List<ListStoryItem>?> {
        repository.getStory()
        return repository.list
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    suspend fun logout() {
        repository.logout()
    }

}