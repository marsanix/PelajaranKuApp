package com.marsanix.pelajarankuapp

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MateriRepository(private val materiDao: MateriDao) {

    val allMateri: LiveData<List<MateriEntity>> = materiDao.getAllMateri()

    @WorkerThread
    fun getMateri(slug: String): MateriEntity {
       return materiDao.getMateriBySlug(slug)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(materi: MateriEntity) {
        materiDao.insert(materi)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(materi: MateriEntity) {
        materiDao.update(materi)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun upsertSetting(materiSetting: MateriSettingEntity) {
        materiDao.upsertSetting(materiSetting)
    }

    @WorkerThread
    fun getMateriSetting(code: String): MateriSettingEntity {
        return materiDao.getSettingByCode(code)
    }

}