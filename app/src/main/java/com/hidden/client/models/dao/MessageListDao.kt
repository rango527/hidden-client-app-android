package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.MessageListEntity
import com.hidden.client.models.entity.ReviewerEntity

@Dao
interface MessageListDao {

    @get:Query("SELECT * FROM MessageList")
    val all: List<MessageListEntity>

    @Query("SELECT * FROM MessageList WHERE myId = :myId")
    fun getListByMessage(myId: Int): List<MessageListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg messageList: MessageListEntity)

    @Query("DELETE FROM MessageList")
    fun deleteAll()

    @Query("DELETE FROM MessageList WHERE id = :id")
    fun deleteByMessageId(id: Int)
}
