package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction

class MainFeature2<S : State>(
    store: Store<S>,
) : BaseFeatureComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                onClick = { dispatch(NavigationAction.Back) },
            ) {
                Text("Back")
            }
            Text("MainFeature2")
            child()
        }
    }

    companion object {
        operator fun invoke() = feature(
            Target,
            MainPage2.Target,
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
