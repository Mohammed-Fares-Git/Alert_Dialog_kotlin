package com.example.mohammed_fares_kotlin_c2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohammed_fares_kotlin_c2.data.repository.SmartPhoneRepository
import com.example.mohammed_fares_kotlin_c2.model.SmartPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmartPhoneViewModel @Inject constructor(
    val repository: SmartPhoneRepository
) : ViewModel() {


    private val _smaartPhoneStateFlow = MutableStateFlow<List<SmartPhone>>(emptyList())
    val smaartPhoneStateFlow: StateFlow<List<SmartPhone>> = _smaartPhoneStateFlow

    init {
        viewModelScope.launch {
            repository.getAll().collectLatest {
                _smaartPhoneStateFlow.value = it
            }
        }
    }


    suspend fun add(smartPhone: SmartPhone): Long {
        return repository.insert(smartPhone)
    }

    suspend fun edit(smartPhone: SmartPhone): Int {
        return repository.update(smartPhone)
    }

    suspend fun remove(smartPhone: SmartPhone): Int {
        return repository.delete(smartPhone)
    }
}