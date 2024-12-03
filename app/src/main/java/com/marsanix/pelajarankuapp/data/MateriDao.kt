package com.marsanix.pelajarankuapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface MateriDao {

    @Insert
    fun insert(materiEntity: MateriEntity)

    @Update
    fun update(materiEntity: MateriEntity)

    @Query("SELECT * FROM materi WHERE slug = :slug")
    fun getMateriBySlug(slug: String): MateriEntity

    @Query("SELECT * FROM materi")
    fun getAllMateri(): LiveData<List<MateriEntity>>

    @Upsert
    fun upsertSetting(materiSettingEntity: MateriSettingEntity)

    @Query("SELECT * FROM settings WHERE code = :code")
    fun getSettingByCode(code: String): MateriSettingEntity

}