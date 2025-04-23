package su.tease.dontforgetthediscount.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import org.koin.android.ext.android.inject
import su.tease.dontforgetthediscount.component.DontForgetTheDiscountComponent
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.mvi.navigation.state.NavigationState
import su.tease.project.design.theme.impl.Theme
import su.tease.project.design.theme.impl.ThemeValue
import su.tease.project.design.theme.impl.switchTheme

class DontForgetTheDiscountActivity : AppCompatActivity() {

    private val dontForgetTheDiscountComponent: DontForgetTheDiscountComponent by inject()
    private val store: Store<*> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        store.dispatcher.dispatch(NavigationAction.Start)

        switchTheme(ThemeValue.LIGHT)

        setContent {
            val finished = store
                .select<NavigationState, Boolean> { finished }
                .collectAsState(false).value

            LaunchedEffect(finished) {
                if (finished) finish()
            }

            Theme {
                dontForgetTheDiscountComponent.Compose()
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
