package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.DashboardTileEntity

@Dao
interface DashboardTileDao {

    @Query("SELECT * FROM DashboardTile WHERE userId like :userId")
    fun getTileList(userId: Int): List<DashboardTileEntity>

    @Insert
    fun insertAll(vararg tiles: DashboardTileEntity): List<Long>

    @Insert
    fun insert(tile: DashboardTileEntity): Long

    @Query("DELETE FROM DashboardTile")
    fun deleteAll()
}