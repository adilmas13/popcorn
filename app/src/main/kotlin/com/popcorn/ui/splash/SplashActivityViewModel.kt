package com.popcorn.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popcorn.domain.usecase.ConfigUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashActivityViewModel @ViewModelInject constructor(
    private val useCase: ConfigUseCase
) : ViewModel() {

    val onSuccess = MutableLiveData<Boolean>()
    val onError = MutableLiveData<Boolean>()

    fun getConfig() {
        viewModelScope.launch {
            useCase.getConfig()
                .catch { onError.value = true }
                .collect { onSuccess.value = true }
        }
    }
}
