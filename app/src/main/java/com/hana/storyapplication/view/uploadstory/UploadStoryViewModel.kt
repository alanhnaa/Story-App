package com.hana.storyapplication.view.uploadstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hana.storyapplication.data.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadStoryViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var isLoading: LiveData<Boolean> = repository.isLoading
    fun uploadStory(file: MultipartBody.Part, description: RequestBody) {
        return repository.uploadImage(file, description)
    }
}