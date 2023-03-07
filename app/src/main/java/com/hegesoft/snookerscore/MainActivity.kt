package com.hegesoft.snookerscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.hegesoft.snookerscore.ui.screens.NavGraphs
import com.hegesoft.snookerscore.ui.screens.Navigator
import com.hegesoft.snookerscore.ui.theme.SnookerScoreTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val animatedNavController = rememberAnimatedNavController()
            val animatedNavHostEngine = rememberAnimatedNavHostEngine()

            SnookerScoreTheme(useDarkTheme = true) {
                Surface {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = 0.dp,
                                end = 0.dp,
                                top = 35.dp,
                                bottom = 5.dp
                            ),
                    ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = animatedNavController,
                            engine = animatedNavHostEngine
                        )
                        navigator.navigationCommand.collectAsState().value.also { direction ->
                            if (direction != null) {
                                animatedNavController.navigate(direction)
                                navigator.acknowledgeNavigation()
                            }
                        }
                        navigator.shouldNavigateBack.collectAsState().value.also { shouldNavigateBack ->
                            if (shouldNavigateBack) {
                                animatedNavController.popBackStack()
                                navigator.acknowledgeNavigation()
                            }
                        }
                    }
                }
            }
        }
    }
}
