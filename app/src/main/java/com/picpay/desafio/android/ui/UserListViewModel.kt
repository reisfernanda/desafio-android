package com.picpay.desafio.android.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.domain.User

class UserListViewModel(
    application: Application,
    private val getUsersUseCase: GetUsersUseCase,
): AndroidViewModel(application) {

    private val _users by lazy { MutableLiveData<List<User>>() }
    val users: LiveData<List<User>> = _users

    init {
        getUsers()
    }

    fun getUsers() {
        getUsersUseCase(
            scope = viewModelScope,
            onSuccess = { list ->
                Log.d("UserListViewModel", list.toString())
                _users.postValue(list)
            }, onError = { throwable ->
                Log.d("UserListViewModel", throwable.message!!)
            }, onLoading = {
                Log.d("UserListViewModel", "loading")
            }
        )
    }
}