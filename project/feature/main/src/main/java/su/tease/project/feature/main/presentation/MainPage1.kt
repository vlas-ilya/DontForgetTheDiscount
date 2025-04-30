package su.tease.project.feature.main.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

@Suppress("UnusedPrivateProperty")
class MainPage1(
    store: Store<*>,
    private val target: Target,
) : BasePageComponent(store) {

    @Composable
    override operator fun invoke() {
        val value = remember { mutableStateOf(false) }
        Column {
            Text(
                text = if (value.value) "Test1" else "Test2",
                modifier = Modifier.clickable { value.value = !value.value }
            )
            if (value.value) {
                Test1()
            } else {
                Test2()
            }
        }
    }

    @Parcelize
    data class Target(val text: String) : NavigationTarget.Page
}

@Composable
@Suppress("ModifierMissing")
fun Test1() {
    val value = remember { mutableIntStateOf(0) }
    Text(
        text = value.intValue.toString(),
        modifier = Modifier.clickable { value.intValue += 1 }
    )
}

@Composable
@Suppress("ModifierMissing")
fun Test2() {
    val value = remember { mutableIntStateOf(0) }
    Text(
        text = value.intValue.toString(),
        modifier = Modifier.clickable { value.intValue += 1 }
    )
}
