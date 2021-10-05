package com.benny.uploadimage.api

import com.benny.uploadimage.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Api by lazy{
        retrofit.create(Api::class.java)
    }
}