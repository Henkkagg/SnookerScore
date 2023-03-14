package com.hegesoftware.snookerscore.ui.screens

import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/*

 */

@Singleton
class Navigator @Inject constructor() {

    private val _navigationCommand = MutableStateFlow<Direction?>(null)
    val navigationCommand = _navigationCommand.asStateFlow()

    private val _shouldNavigateBack = MutableStateFlow(false)
    val shouldNavigateBack = _shouldNavigateBack.asStateFlow()

    fun navigate(direction: Direction) {
        _navigationCommand.value = direction
    }

    fun popBackStack() {
        _shouldNavigateBack.value = true
    }

    fun acknowledgeNavigation() {
        _navigationCommand.value = null
        _shouldNavigateBack.value = false
    }
}
