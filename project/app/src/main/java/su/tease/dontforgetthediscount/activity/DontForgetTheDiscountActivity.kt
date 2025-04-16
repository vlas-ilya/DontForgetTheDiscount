package su.tease.dontforgetthediscount.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import org.koin.android.ext.android.inject
import su.tease.dontforgetthediscount.component.DontForgetTheDiscountComponent
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.selector.select
import su.tease.project.core.mvi.navigation.selector.page
import su.tease.project.core.mvi.navigation.state.FinishNavigationTarget
import su.tease.project.design.theme.impl.Theme
import su.tease.project.design.theme.impl.ThemeValue
import su.tease.project.design.theme.impl.switchTheme

class DontForgetTheDiscountActivity : AppCompatActivity() {

    private val dontForgetTheDiscountComponent: DontForgetTheDiscountComponent<*> by inject()
    private val store: Store<*> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        switchTheme(ThemeValue.LIGHT)
        setContent {
            val page = store.select(page()).collectAsState(null).value

            LaunchedEffect(page) {
                if (page?.name == FinishNavigationTarget) finish()
            }

            Theme {
                dontForgetTheDiscountComponent.Compose()
            }
        }
    }
}
