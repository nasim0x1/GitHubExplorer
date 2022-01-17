package com.github.expo.ui.main.viewmodel

import androidx.lifecycle.*
import com.github.expo.data.model.UserRepositoryItem
import com.github.expo.data.repository.UserRepoRepository
import com.github.expo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/* 
 * Created by nasim on 1/16/22  
 */
class UserRepositoryViewModel(private val repository: UserRepoRepository) : ViewModel() {
    private val _response = MutableLiveData<Resource<List<UserRepositoryItem>>>()

    fun request(username:String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.request(username,_response)
        }
    }

    val response: LiveData<Resource<List<UserRepositoryItem>>>
        get() = _response
}