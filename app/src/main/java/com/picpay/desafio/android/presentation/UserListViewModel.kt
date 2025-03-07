package com.picpay.desafio.android.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.commons.Status
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.domain.User

class UserListViewModel(
    application: Application,
    private val getUsersUseCase: GetUsersUseCase,
): AndroidViewModel(application) {

    private val _users by lazy { MutableLiveData<List<User>>() }
    val users: LiveData<List<User>> = _users

    private val _status by lazy { MutableLiveData<Status>() }
    val status: LiveData<Status> = _status

    init {
        getUsers()
    }

    fun getUsers(forceUpdate: Boolean = false) {
        UserFragment.countingIdlingResource?.increment()
        getUsersUseCase.run(
            scope = viewModelScope,
            params = forceUpdate,
            onSuccess = { users ->
                if (users.isEmpty()) {
                    _status.postValue(Status.EMPTY)
                } else {
                    _status.postValue(Status.SUCCESS)
                }
                _users.postValue(users)
                UserFragment.countingIdlingResource?.decrement()
            }, onError = {
                _status.postValue(Status.FAILURE)
                UserFragment.countingIdlingResource?.decrement()
            }, onLoading = {
                if (it) _status.postValue(Status.LOADING)
            }
        )
    }
}