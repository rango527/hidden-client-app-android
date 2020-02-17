package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.ProcessEntity

@Dao
interface ProcessDao {

    @get:Query("SELECT * FROM Process")
    val all: List<ProcessEntity>

    @Query("SELECT * FROM Process WHERE myId = :myId")
    fun getProcess(myId: Int): List<ProcessEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg process: ProcessEntity)

    @Query("DELETE FROM Process")
    fun deleteAll()
}