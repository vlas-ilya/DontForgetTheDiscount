package su.tease.dontforgetthediscount.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import su.tease.dontforgetthediscount.component.DontForgetTheDiscountComponent
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.mvi.navigation.state.NavigationState
import su.tease.project.design.theme.impl.Theme
import su.tease.project.design.theme.impl.ThemeValue
import su.tease.project.design.theme.impl.switchTheme

class DontForgetTheDiscountActivity : ComponentActivity() {

    private val dontForgetTheDiscountComponent: DontForgetTheDiscountComponent by inject {
        val windowProvider = { window }
        parametersOf(windowProvider)
    }

    private val store: Store<*> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = Color.Transparent.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(scrim = Color.Transparent.toArgb()),
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        store.dispatcher.dispatch(NavigationAction.Start)

        switchTheme(ThemeValue.LIGHT)

        setContent {
            val finished = remember { store.select<NavigationState, Boolean> { finished } }
                .collectAsState()
                .value

            LaunchedEffect(finished) {
                if (finished) finish()
            }

            Theme {
                dontForgetTheDiscountComponent.ComposeDontForgetTheDiscountComponent()
            }
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                store.dispatcher.dispatch(NavigationAction.Back)
            }
        }
}
