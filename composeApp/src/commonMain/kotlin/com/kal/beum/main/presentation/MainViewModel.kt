package com.kal.beum.main.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onProgress
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.main.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.onSuccess

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.onStart {
        println("onStart")
        getAppEntity()
        getUserInfo()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    private fun getAppEntity() {
        appRepository.getAppEntity().onEach { result ->
            println("getAppEntity : $result")
            _state.update {
                it.copy(
                    isDevil = result.isDevil, isOnboardingDone = result.isOnBoardingDone
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun devilToggle(isDevil: Boolean) {
        println("devilToggle ")
        viewModelScope.launch {
            appRepository.setIsDevil(isDevil)
            appRepository.getAppEntity().onEach { result ->
                println("result : $result")
                _state.update { it.copy(isDevil = result.isDevil) }
            }.launchIn(this)
        }
    }

    private fun getUserInfo() {
        println("getUserInfo")
        appRepository.getLoginInfo().onEach { result ->

            if (result != null) {
                _state.update { it.copy(userInfo = result) }
//                delay(3000)
                _state.update { it.copy(isSplashDone = true) }
            }
        }.launchIn(viewModelScope)
    }

    private fun pushFullScreen(screen: (@Composable () -> Unit)) {
        println("pushFullScreen")
        _state.update { it.copy(fullScreen = _state.value.fullScreen + screen) }
    }

    private fun popFullScreen() {
        println("popFullScreen")
        _state.update { it.copy(fullScreen = state.value.fullScreen.dropLast(1)) }
    }

    private fun clearFullScreen() {
        println("clearFullScreen")
        _state.update { it.copy(fullScreen = emptyList(), isSplashDone = false) }
    }

    fun toggleFullScreen() {
        println("toggleFullScreen")
        _state.update { it.copy(isFullScreen = !it.isFullScreen) }
    }

    fun socialLogin(socialCode: Int) {
        println("socialLogin : $socialCode")
        appRepository.login(socialCode).onEach { result ->
            println("socialLogin~~~~ : $result")
            when (result) {
                is Result.Error -> {
                    println("Error :")
                    _state.update { it.copy(isProgress = false) }
                }

                is Result.Success -> {
                    println("Success :")
                    _state.update {
                        it.copy(
                            userInfo = result.data, isSplashDone = true, isProgress = false
                        )
                    }
                }

                is Result.Progress -> {
                    _state.update { it.copy(isProgress = true) }
                    println("progress :")
                }
            }

        }.launchIn(viewModelScope)
    }

    fun logout() {
        val userInfo = state.value.userInfo
        if (userInfo != null) {
            appRepository.logout(userInfo).onEach { result ->
                result.onSuccess {
                    _state.update { it.copy(userInfo = null, isProgress = false) }
                }.onProgress {
                    _state.update { it.copy(isProgress = true) }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.ToggleDevil -> devilToggle(action.isDevil)
            is MainAction.PushFullScreen -> pushFullScreen(action.screen)
            is MainAction.PopFullScreen -> popFullScreen()
            is MainAction.ClearFullScreen -> clearFullScreen()
            is MainAction.ToastMessage -> {
                _state.update { it.copy(showToast = action.toastInfo) }
            }

            is MainAction.LogOut -> logout()
        }
    }
}