package com.example

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.navigation.DracuRoutes
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.FigureDetailScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.DracuversusTheme
import com.example.ui.viewmodel.MuseumViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        )
        setContent {
            DracuversusTheme {
                DracuversusApp()
            }
        }
    }
}

@Composable
fun DracuversusApp(
    viewModel: MuseumViewModel = viewModel()
) {
    val navController = rememberNavController()
    val catalogState by viewModel.catalogState.collectAsStateWithLifecycle()
    val narrationState by viewModel.narrationState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = DracuRoutes.WELCOME,
        enterTransition = {
            fadeIn(animationSpec = tween(280)) + slideInHorizontally(
                initialOffsetX = { it / 12 },
                animationSpec = tween(280)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(220))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(250))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(220)) + slideOutHorizontally(
                targetOffsetX = { it / 12 },
                animationSpec = tween(220)
            )
        }
    ) {
        composable(route = DracuRoutes.WELCOME) {
            WelcomeScreen(
                onEnterMuseum = {
                    navController.navigate(DracuRoutes.CATALOG) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = DracuRoutes.CATALOG) {
            CatalogScreen(
                state = catalogState,
                onSelectEra = viewModel::selectEra,
                onSelectYear = viewModel::selectYear,
                onSearchQueryChange = viewModel::updateSearchQuery,
                onClearFilters = viewModel::clearFilters,
                onFigureClick = { figureId ->
                    navController.navigate(DracuRoutes.figureDetail(figureId)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = DracuRoutes.FIGURE_DETAIL,
            arguments = listOf(navArgument("figureId") { type = NavType.StringType })
        ) { backStackEntry ->
            val figureId = backStackEntry.arguments?.getString("figureId").orEmpty()
            val figureFlow = remember(figureId) { viewModel.observeFigureById(figureId) }
            val figure by figureFlow.collectAsStateWithLifecycle(initialValue = null)

            FigureDetailScreen(
                figure = figure,
                narrationState = narrationState,
                onPrepareAudio = viewModel::prepareAudioForFigure,
                onPlayOrResumeAudio = viewModel::playOrResumeAudio,
                onPauseAudio = viewModel::pauseAudio,
                onStopAudio = viewModel::stopAudio,
                onRestartAudio = viewModel::restartAudio,
                onAttachPhoto = viewModel::importPhotoFromUri,
                onRemovePhoto = viewModel::removeFigurePhoto,
                onNavigateBack = {
                    viewModel.stopAudio()
                    navController.popBackStack()
                }
            )
        }
    }
}
