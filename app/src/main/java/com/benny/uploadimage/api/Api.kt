package com.benny.uploadimage.api

import com.benny.uploadimage.model.ServerResponse
import com.benny.uploadimage.model.UploadModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("upload.php")
    suspend fun uploadImage(
        @Body request: UploadModel
    ): Response<ServerResponse>
}