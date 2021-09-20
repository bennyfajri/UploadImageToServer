package com.benny.uploadimage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benny.uploadimage.repository.Repository

class ViewModelsFactory(
    private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModels(repository) as T
    }
}