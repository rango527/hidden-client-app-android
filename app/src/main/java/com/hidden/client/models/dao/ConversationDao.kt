package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.ConversationEntity

@Dao
interface ConversationDao {

    @get:Query("SELECT * FROM Message")
    val all: List<ConversationEntity>

    @Query("SELECT * FROM Message WHERE myId = :myId AND conversationId = :conversationId")
    fun getConversationSelect(myId: Int, conversationId: Int): List<ConversationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg message: ConversationEntity)

    @Query("DELETE FROM Message")
    fun deleteAll()

}