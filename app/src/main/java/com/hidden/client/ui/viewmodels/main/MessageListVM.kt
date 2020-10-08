package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.content.LocusId
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ConversationApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.models.dao.ConversationDao
import com.hidden.client.models.dao.MessageListDao
import com.hidden.client.models.entity.ConversationEntity
import com.hidden.client.models.entity.MessageListEntity
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.models.json.ConversationJson
import com.hidden.client.ui.adapters.MessageListAdapter
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MessageListVM(
    private val context: Context,
    private val conversationDao: ConversationDao,
    private val messageListDao: MessageListDao
) : RootVM() {

    @Inject
    lateinit var conversationApi: ConversationApi
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

//    // To Reload
//    private val _navigateReload = MutableLiveData<Event<Boolean>>()
//    val navigateReload: LiveData<Event<Boolean>>
//        get() = _navigateReload

    var conversationId: Int = 1
        set(value) {
            field = value
        }

    var isMessage: Boolean = false

    val messageListAdapter: MessageListAdapter = MessageListAdapter()

    val candidateName: MutableLiveData<String> = MutableLiveData("")
    val candidateAvatar: MutableLiveData<String> = MutableLiveData("")
    val jobTitle: MutableLiveData<String> = MutableLiveData("")

    private var subscription: Disposable? = null

//    init {
//        loadMessage(false, conversationId)
//    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadMessage(cashMode: Boolean, conversationId: Int) {
        val apiObservable: Observable<ConversationEntity>
        if (cashMode) {
            apiObservable =
                Observable.fromCallable { conversationDao.getConversationSelect(AppPreferences.myId, conversationId) }
                    .concatMap { dbMessageData ->
                        val observable = if (dbMessageData.isEmpty()) {
                            conversationApi.getMessage(
                                AppPreferences.apiAccessToken,
                                conversationId
                            )
                                .concatMap { apiMessage ->
                                    Observable.just(parseJsonResult(apiMessage))
                                }
                        } else {
                            val conversationEntity = dbMessageData[0]
                            val messageList = messageListDao.getListByMessage(AppPreferences.myId)
                            Observable.just(parseEntityResult(conversationEntity, messageList))
                        }
                        observable
                    }
        } else {
            apiObservable = Observable.fromCallable { }
                .concatMap {
                    conversationApi.getMessage(AppPreferences.apiAccessToken, conversationId)
                        .concatMap { apiMessage ->
                            Observable.just(parseJsonResult(apiMessage))
                        }
                }
        }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMessageStart() }
            .doOnTerminate { onRetrieveMessageFinish() }
            .subscribe(
                { result -> onRetrieveMessageSuccess(result) },
                { error -> onRetrieveMessageError(error) }
            )
    }

    private fun parseJsonResult(json: ConversationJson): ConversationEntity {
        val conversation: ConversationEntity = json.toConversationEntity(conversationId, AppPreferences.myId)
        val messageList: ArrayList<MessageListEntity> = arrayListOf()
        messageList.addAll(json.toMessageEntityList(AppPreferences.myId))

        // Update Conversation & Message Db
        conversationDao.deleteAll()
        messageListDao.deleteAll()

        // Update Message Table
        conversationDao.insertAll(conversation)
        messageListDao.insertAll(*messageList.toTypedArray())

        return parseEntityResult(conversation, messageList)
    }

    private fun parseEntityResult(conversation: ConversationEntity, messageList: List<MessageListEntity>): ConversationEntity {

        val userManagerList: ArrayList<MessageListEntity> = arrayListOf()

        for(message in messageList) {
            userManagerList.add(message)
        }

        conversation.setMessageList(userManagerList)

        return conversation
    }

    private fun onRetrieveMessageStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveMessageFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveMessageSuccess(conversation: ConversationEntity) {
        messageListAdapter.updateMessageList(conversation.getMessageList(), conversationId)
    }

    private fun onRetrieveMessageError(e: Throwable) {
        e.printStackTrace()
    }
}