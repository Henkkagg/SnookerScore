package com.hegesoftware.snookerscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.hegesoftware.snookerscore.ui.screens.NavGraphs
import com.hegesoftware.snookerscore.ui.screens.Navigator
import com.hegesoftware.snookerscore.ui.theme.SnookerScoreTheme
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

        val appUpdateManager = AppUpdateManagerFactory.create(this.applicationContext)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                && appUpdateInfo.updatePriority() >= 1
            ) {
                appUpdateManager.startUpdateFlow(
                    appUpdateInfo,
                    this,
                    AppUpdateOptions.defaultOptions(AppUpdateType.IMMEDIATE)
                )
            }
        }

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
                                bottom = 10.dp
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
