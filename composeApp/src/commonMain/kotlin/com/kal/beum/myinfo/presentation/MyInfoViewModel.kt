package com.kal.beum.myinfo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.myinfo.domain.MyContent
import com.kal.beum.myinfo.domain.MyInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyInfoViewModel(private val myInfoRepository: MyInfoRepository) : ViewModel() {
    private val _state = MutableStateFlow(MyInfoState())
    val state = _state.onStart {
        getMyInfo()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    private fun getMyContent(userId: Int) {
        viewModelScope.launch {
            myInfoRepository.getMyContents(userId).onSuccess { myContents ->
                _state.update { it.copy(myContent = myContents) }
            }
        }.start()
    }

    private fun getMyReply(userId: Int) {
        viewModelScope.launch {
            myInfoRepository.getMyReply(userId).onSuccess { myReply ->
                _state.update { it.copy(myReply = myReply) }
            }
        }.start()
    }

    private fun getMyInfo() {
        println("getMyInfo")
        viewModelScope.launch {
            myInfoRepository.getMyInfo().onSuccess { myInfo ->
                println(myInfo)
                _state.update { it.copy(myInfo = myInfo) }
                getMyContent(myInfo.userId.toInt())
                getMyReply(myInfo.userId.toInt())
            }
        }.start()
    }

    fun reportUser(reportContent: MyContent) {
    }
}