package com.marsanix.pelajarankuapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MateriViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MateriRepository
    val allMateri: LiveData<List<MateriEntity>>

    init {
        val materiDao = MateriDatabase.getInstance(application).materiDao()
        repository = MateriRepository(materiDao)
        allMateri = repository.allMateri

    }

    private val _materi = MutableLiveData<MateriEntity>()
    val materi: LiveData<MateriEntity> get() = _materi
    fun getMateri(slug: String) = viewModelScope.launch {
        val result = repository.getMateri(slug)
        _materi.postValue(result)
    }

    fun insert(materi: MateriEntity) = viewModelScope.launch {
        repository.insert(materi)
    }

    fun upsertSetting(materiSetting: MateriSettingEntity) = viewModelScope.launch {
        repository.upsertSetting(materiSetting)
    }

    private val _materiSetting = MutableLiveData<MateriSettingEntity>()
    val materiSetting: LiveData<MateriSettingEntity> get() = _materiSetting
    fun getMateriSetting(code: String) = viewModelScope.launch {
        val result = repository.getMateriSetting(code)
        _materiSetting.postValue(result)
    }

    private val _lastActivity = MutableLiveData<MateriSettingEntity>()
    val lastActivity: LiveData<MateriSettingEntity> get() = _lastActivity
    fun getLastActivity() = viewModelScope.launch {
        val result = repository.getMateriSetting("aktivitas_terakhir")
        _lastActivity.postValue(result)
    }

    fun update(materi: MateriEntity) = viewModelScope.launch {
        repository.update(materi)
    }


}