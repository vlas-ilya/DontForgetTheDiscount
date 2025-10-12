@file:Suppress("ForbiddenComment")

package su.tease.core.mvi.component.component.container

import android.view.Window
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.impl.BaseMviComponent
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.app
import su.tease.project.core.mvi.navigation.selector.feature
import su.tease.project.core.mvi.navigation.selector.page
import su.tease.project.core.utils.ext.hideSystemUI
import su.tease.project.core.utils.ext.isNavigationBarVisible
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.showSystemUI
import su.tease.project.core.utils.function.pipe

@Immutable
data class RootConfig(
    val isFullscreen: Boolean = false,
    val isNavigationBarVisible: Boolean = true,
) {
    val hasSystemNavigationBar: Boolean = isFullscreen.not() || isNavigationBarVisible
}

@Immutable
data class AppConfig(
    val hasNavigationBar: Boolean = true,
)

@Immutable
data class FeatureConfig(
    val action: AppAction? = null,
) {
    @Immutable
    data class AppAction(
        @DrawableRes val icon: Int,
        val onClick: () -> Unit,
    )
}

val LocalRootConfig = compositionLocalOf { RootConfig() }
val LocalAppConfig = compositionLocalOf { AppConfig() }
val LocalFeatureConfig = compositionLocalOf { FeatureConfig() }

class RootContainer(
    store: Store<*>,
    private val navigationTargetResolver: AppNavigationTargetResolver,
    private val windowProvider: () -> Window,
) : BaseMviComponent(store) {

    @Composable
    @Suppress("ModifierMissing")
    fun ComposeRootContainer() {
        val app = selectAsState(app())
            .map { navigationTargetResolver.resolve(it.id, it.name, it) }
            .value

        val feature = selectAsState(feature())
            .map { navigationTargetResolver.resolve(it.id, it.name, it) }
            .value

        val page = selectAsState(page())
            .map { navigationTargetResolver.resolve(it.id, it.name) }
            .value

        val rootConfig = remember(app.rootConfig.value, feature.rootConfig.value, page.rootConfig.value) {
                RootConfig()
                    .pipe(app.rootConfig.value)
                    .pipe(feature.rootConfig.value)
                    .pipe(page.rootConfig.value)
                    .invoke()
                    .copy(isNavigationBarVisible = windowProvider().isNavigationBarVisible())
            }

        val appConfig =
            remember(app.appConfig.value, feature.appConfig.value, page.appConfig.value) {
                AppConfig()
                    .pipe(app.appConfig.value)
                    .pipe(feature.appConfig.value)
                    .pipe(page.appConfig.value)
                    .invoke()
            }

        val featureConfig = remember(feature.featureConfig.value, page.featureConfig.value) {
            FeatureConfig()
                .pipe(feature.featureConfig.value)
                .pipe(page.featureConfig.value)
                .invoke()
        }

        LaunchedEffect(rootConfig) {
            val isFullscreen = rootConfig.isFullscreen
            if (isFullscreen) {
                windowProvider().hideSystemUI()
            } else {
                windowProvider().showSystemUI()
            }
        }

        CompositionLocalProvider(
            LocalRootConfig provides rootConfig,
            LocalAppConfig provides appConfig,
            LocalFeatureConfig provides featureConfig,
        ) {
            app {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(
                        Modifier.weight(1F)
                    ) {
                        feature {
                            page()
                        }
                    }

                    AnimatedVisibility(
                        visible = appConfig.hasNavigationBar,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically(),
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            app.ComposeNavigationBar()
                        }
                    }
                }
            }
        }
    }
}
