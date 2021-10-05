package com.benny.uploadimage.repository

import com.benny.uploadimage.api.RetrofitInstance
import com.benny.uploadimage.model.ServerResponse
import com.benny.uploadimage.model.UploadModel
import retrofit2.Response

class Repository {
    suspend fun insertImage(nama: String, images: String): Response<ServerResponse>{
        return RetrofitInstance.api.uploadImage(nama, images)
    }
}