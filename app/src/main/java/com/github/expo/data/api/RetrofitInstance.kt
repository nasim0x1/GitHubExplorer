package com.github.expo.data.api

import com.github.expo.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/* 
 * Created by nasim on 1/16/22  
 */
object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.base)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val getInstance: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }
}