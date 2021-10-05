package com.benny.uploadimage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benny.uploadimage.model.ServerResponse
import com.benny.uploadimage.model.UploadModel
import com.benny.uploadimage.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModels(private val repository: Repository) : ViewModel(){

    val myResponse: MutableLiveData<Response<ServerResponse>> = MutableLiveData()

    fun insertImage(name: String, image: String){
        viewModelScope.launch {
            val response = repository.insertImage(name, image)
            myResponse.value = response
        }
    }
}