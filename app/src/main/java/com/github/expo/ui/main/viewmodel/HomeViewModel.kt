package com.github.expo.ui.main.viewmodel

import androidx.lifecycle.*
import com.github.expo.data.model.UserProfileModel
import com.github.expo.data.repository.UserProfileRepository
import com.github.expo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/* 
 * Created by nasim on 1/16/22  
 */
class HomeViewModel(private val repository: UserProfileRepository) : ViewModel() {
    private val _response = MutableLiveData<Resource<UserProfileModel>>()

    fun request(username:String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.request(username,_response)
        }
    }

    val response: LiveData<Resource<UserProfileModel>>
        get() = _response
}