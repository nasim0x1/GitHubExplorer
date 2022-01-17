package com.github.expo.data.api

import com.github.expo.data.model.UserProfileModel
import com.github.expo.data.model.UserRepositoryItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


/*
 * Created by nasim on 1/16/22  
 */
interface ApiServices {

    @GET("users/{username}")
    suspend fun getUserProfileDetails(@Path("username") username:String):Response<UserProfileModel>

    @GET("users/{username}/repos")
    suspend fun getUserRepoDetails(@Path("username") username:String):Response<List<UserRepositoryItem>>

}