package com.hegesoft.snookerscore.ui.screens

import android.util.Log
import androidx.navigation.NavController
import com.hegesoft.snookerscore.ui.screens.destinations.DirectionDestination
import com.hegesoft.snookerscore.ui.screens.destinations.NewGameScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
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
