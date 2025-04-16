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

class MainPage3<S : State>(
    store: Store<S>,
) : BasePageComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose() {
        Column {
            Text("MainPage3")
            Button(
                onClick = { dispatch(NavigationAction.ForwardToPage(MainPage4.Target)) },
            ) {
                Text("Forward To MainPage4")
            }
        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}
