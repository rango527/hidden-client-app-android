package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.DashboardTileContentEntity

@Dao
interface DashboardTileContentDao {

    @get:Query("SELECT * FROM DashboardTileContent")
    val all: List<DashboardTileContentEntity>

    @Query("SELECT * FROM DashboardTileContent WHERE pDashboardTileId = :tileId")
    fun getContentByTileId(tileId: Int): List<DashboardTileContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg contents: DashboardTileContentEntity)

    @Query("DELETE FROM DashboardTileContent")
    fun deleteAll()

    @Query("DELETE FROM DashboardTileContent WHERE pDashboardTileId = :tileId")
    fun deleteByTileId(tileId: Int)
}