package com.hana.storyapplication.view.signup

import androidx.lifecycle.ViewModel
import com.hana.storyapplication.data.UserRepository
import com.hana.storyapplication.data.response.RegisterResponse

class SignupViewModel(private var repository: UserRepository) : ViewModel() {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return repository.register(name, email, password)
    }
}