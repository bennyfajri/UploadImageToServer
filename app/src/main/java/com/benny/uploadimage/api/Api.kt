package com.benny.uploadimage.api

import com.benny.uploadimage.model.ServerResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("upload.php")
    suspend fun uploadImage(
        @Field("name") name: String?,
        @Field("image") image: String?
    ): Response<ServerResponse>
}