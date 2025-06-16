package com.kal.beum.main.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.main.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    fun setFullScreen(screen: (@Composable () -> Unit)? = null) {
        println("setFullScreen")
        _state.update { it.copy(fullScreen = screen) }
    }

    fun toggleFullScreen() {
        println("toggleFullScreen")
        _state.update { it.copy(isFullScreen = !it.isFullScreen) }
    }

    fun socialLogin(socialCode: Int) {
        println("socialLogin : $socialCode")
        appRepository.login(socialCode).onEach { result ->
            println("socialLogin~~~~ : $result")
            _state.update { it.copy(userInfo = result, isSplashDone = true) }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.ToggleDevil -> devilToggle(action.isDevil)
            is MainAction.SetFullScreen -> setFullScreen(action.screen)
        }
    }
}