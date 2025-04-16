package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction

class MainPage1<S : State>(
    store: Store<S>,
    private val target: Target,
) : BasePageComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose() {
        Column {
            Text("MainPage1 ${target.text}")

            Button(
                onClick = { dispatch(NavigationAction.ForwardToPage(MainPage2.Target)) },
            ) {
                Text("Forward To MainPage2")
            }
        }
    }

    @Parcelize
    data class Target(val text: String) : NavigationTarget.Page
}
