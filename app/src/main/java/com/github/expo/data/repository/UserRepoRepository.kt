package com.github.expo.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.expo.data.api.RetrofitInstance
import com.github.expo.data.model.UserRepositoryItem
import com.github.expo.utils.NetworkUtils
import com.github.expo.utils.Resource


/* 
 * Created by nasim on 1/16/22  
 */
class UserRepoRepository(private val context: Context, private val instance: RetrofitInstance) {

    suspend fun request(
        username: String,
        response: MutableLiveData<Resource<List<UserRepositoryItem>>>
    ) {
        response.postValue(Resource.loading())
        if (NetworkUtils.isInternetAvailable(context)) {
            try {
                val results = instance.getInstance.getUserRepoDetails(username)
                if (results.code() == 200) {
                    response.postValue(Resource.success(results.body()))
                } else if (results.code() == 403) {
                    response.postValue(Resource.error("API rate limit exceeded"))
                } else {
                    response.postValue(Resource.error("Username not found!"))
                }
            } catch (e: Exception) {
                Log.d("log0x1", e.message.toString())
                response.postValue(Resource.error("Unknown error occurred!"))
            }
        } else {
            response.postValue(Resource.error("No internet connection!"))
        }
    }
}