package com.kal.beum.community.presentation

// BaseSideEffect를 상속받았으므로 ShowToast를 자동으로 포함하게 됩니다.
sealed interface CommunitySideEffect {
    data class ShowToast(val message: String) : CommunitySideEffect

}